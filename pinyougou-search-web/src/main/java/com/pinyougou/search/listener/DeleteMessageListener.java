package com.pinyougou.search.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;
import java.util.Arrays;

public class DeleteMessageListener implements SessionAwareMessageListener<ObjectMessage>{

    @Reference(timeout = 10000)
    private ItemSearchService itemSearchService;
    @Override
    public void onMessage(ObjectMessage objectMessage, Session session) throws JMSException {
        System.out.println("------DeleteMessage--------");
        Long[] ids = (Long[]) objectMessage.getObject();
        System.out.println("ids: " + Arrays.toString(ids));
        itemSearchService.delete(Arrays.asList(ids));
    }
}
