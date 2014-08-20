package ace.demo.appl;

import ace.demo.service.CONF;
import com.aliyun.mqs.client.CloudQueue;
import com.aliyun.mqs.client.DefaultMQSClient;
import com.aliyun.mqs.client.MQSClient;
import com.aliyun.mqs.common.ServiceException;
import com.aliyun.mqs.model.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MqsInfo extends HttpServlet {
    private  MQSClient client = null;
    @Override
    public void init(){
        client =new DefaultMQSClient(CONF.mqsUrl, CONF.accessId, CONF.accessKey);
    }
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException	{
        if (client == null){
            resp.getWriter().println("mqs is null");
        }else {
            try {
                CloudQueue queue = client.getQueueRef(CONF.mqsQueueName);
                Message msg = queue.popMessage();
                while (msg != null) {
                    String messageBody = msg.getMessageBodyAsString();
                    String msgId = msg.getMessageId();
                    String receiptHandle = msg.getReceiptHandle();
                    resp.getWriter().println(messageBody);
                    msg = queue.popMessage();
                }

                resp.getWriter().println("get msg from mqs ok");
            } catch (ServiceException ex) {
                //do something LOG
            }
        }

	}
}


