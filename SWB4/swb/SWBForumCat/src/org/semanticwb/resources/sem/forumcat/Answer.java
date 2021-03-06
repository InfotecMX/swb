package org.semanticwb.resources.sem.forumcat;

import java.util.Iterator;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.User;
import org.semanticwb.portal.indexer.SWBIndexer;


public class Answer extends org.semanticwb.resources.sem.forumcat.base.AnswerBase 
{
    static {
        if (SWBPortal.getIndexMgr() != null)
        {
            SWBIndexer index = SWBPortal.getIndexMgr().getDefaultIndexer();
            if (index != null)
            {
                index.registerParser(Answer.class, new AnswerParser());
            }
        }
        
    }

    public Answer(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean userHasVoted(User user) {
        boolean ret = false;
        Iterator<AnswerVote> votes = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(this);
        while (votes.hasNext() && !ret) {
            AnswerVote vote = votes.next();
            if (vote.getAnsUserVote().getURI().equals(user.getURI())) {
                ret = true;
            }
        }
        return ret;
    }

    public boolean isAnonymous() {
        boolean ret = true;
        if (getCreator() != null) {
            ret = false;
        }
        return ret;
    }
}
