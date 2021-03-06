package org.semanticwb.resources.sem.forumcat;

import java.util.Iterator;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.User;
import org.semanticwb.portal.indexer.SWBIndexer;

public class Question extends org.semanticwb.resources.sem.forumcat.base.QuestionBase
{

    static
    {
        if (SWBPortal.getIndexMgr() != null)
        {
            SWBIndexer index = SWBPortal.getIndexMgr().getDefaultIndexer();
            if (index != null)
            {
                index.registerParser(Question.class, new QuestionParser());
            }
        }
    }

    public Question(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isUserSubscribed(User user)
    {
        boolean ret = false;
        Iterator<QuestionSubscription> subscriptions = QuestionSubscription.ClassMgr.listQuestionSubscriptionByQuestionObj(this);
        while (subscriptions.hasNext() && !ret)
        {
            QuestionSubscription subscription = subscriptions.next();
            if (subscription.getUserObj().getURI().equals(user.getURI()))
            {
                ret = true;
            }
        }

        return ret;
    }

    public boolean userHasVoted(User user)
    {
        boolean ret = false;
        Iterator<QuestionVote> votes = QuestionVote.ClassMgr.listQuestionVoteByQuestionVote(this);
        while (votes.hasNext() && !ret)
        {
            QuestionVote vote = votes.next();
            if (vote.getUserVote().getURI().equals(user.getURI()))
            {
                ret = true;
            }
        }

        return ret;
    }

    public boolean userHasAnswered(User user)
    {
        boolean ret = false;
        Iterator<Answer> answers = listAnswerInvs();
        while (!ret && answers.hasNext())
        {
            Answer a = answers.next();
            if (a.getCreator().getURI().equals(user.getURI()))
            {
                ret = true;
            }
        }
        return ret;
    }

    public boolean isAnonymous()
    {
        boolean ret = true;
        if (getCreator() != null)
        {
            ret = false;
        }
        return ret;
    }
}
