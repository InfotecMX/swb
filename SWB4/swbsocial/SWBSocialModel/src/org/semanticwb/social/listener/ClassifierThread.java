/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener;

import java.net.SocketException;
import java.util.LinkedList;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.Post;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.util.SendPostThread;

/**
 *
 * @author jorge.jimenez
 */
public class ClassifierThread extends java.lang.Thread {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(SendPostThread.class);
    /** The emails. */
    Post post = null;

    /**
     * Creates a new instance of WBMessageServer.
     *
     * @throws SocketException the socket exception
     */
    public ClassifierThread(Post post) throws java.net.SocketException {
        this.post = post;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        try
        {
            String words2classify = null;
            if (post instanceof MessageIn) {
                MessageIn messageIn = (MessageIn) post;
                words2classify = messageIn.getMsg_Text();

            } else if (post instanceof PhotoIn) {
                PhotoIn photoIn = (PhotoIn) post;
                words2classify = photoIn.getTitle() + photoIn.getDescription();
            } else if (post instanceof VideoIn) {
                VideoIn videoIn = (VideoIn) post;
                words2classify = videoIn.getTitle() + videoIn.getDescription();
            }
            new SentimentalDataClassifier(post, words2classify);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
