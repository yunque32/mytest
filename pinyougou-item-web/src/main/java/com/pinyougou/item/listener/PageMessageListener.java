package com.pinyougou.item.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.sellergoods.service.GoodsService;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

public class PageMessageListener implements SessionAwareMessageListener<TextMessage> {

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;
    @Reference(timeout = 10000)
    private GoodsService goodsService;
    @Value("${page.dir}")
    private String pageDir;

    @Override
    public void onMessage(TextMessage textMessage, Session session) throws JMSException {
        try {
            System.out.println("------------");
            String goodsId = textMessage.getText();
            System.out.println("goodsId"+goodsId);
            Template template = freeMarkerConfig.getConfiguration().getTemplate("item.ftl");

            Map<String, Object> dataModel = goodsService.getItem(Long.valueOf(goodsId));
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(pageDir + goodsId + ".html"), "UTF-8");
            template.process(dataModel, writer);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            System.out.println("生成静态模板异常");
            throw new RuntimeException(e);
        }
    }
}
