package com.pinyougou.item.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.File;
import java.util.Arrays;

public class DeleteMessageListener implements SessionAwareMessageListener<ObjectMessage> {

    @Value("${page.dir}")
    private  String pageDir;

    @Override
    public void onMessage(ObjectMessage objectMessage, Session session) throws JMSException {
        System.out.println("-------");
        Long[] goodsIds = (Long[]) objectMessage.getObject();
        System.out.println("goodsId:"+ Arrays.toString(goodsIds));
        for(Long goodsId:goodsIds){
            File file = new File(pageDir + goodsId + ".html");
            if (file.exists()){
                file.delete();
            }
        }
    }
}
