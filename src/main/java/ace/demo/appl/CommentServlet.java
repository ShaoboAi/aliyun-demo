package ace.demo.appl;


import ace.demo.service.*;
import com.aliyun.mqs.client.CloudQueue;
import com.aliyun.mqs.client.DefaultMQSClient;
import com.aliyun.mqs.model.Message;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.sls.LogItem;
import com.aliyun.openservices.sls.SLSClient;
import com.aliyun.openservices.sls.SlsException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class CommentServlet extends  HttpServlet {
	private  Comments  comments   = null;
    private  static DefaultMQSClient mqsClient = null;
    private static Logger logger = LoggerFactory.getLogger(CommentServlet.class);
    public CommentServlet() {
    	super();
    }

	public void init() throws ServletException {
        super.init();
        comments = new Comments();
        mqsClient = new DefaultMQSClient(CONF.mqsUrl, CONF.accessId, CONF.accessKey);

    }

	public void destroy() {

	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

        String comment = request.getParameter("comment");
        comments.put(comment);

        CloudQueue queue = mqsClient.getQueueRef(CONF.mqsQueueName);
        Message msg = new Message();
        msg.setMessageBody(comment);
        queue.putMessage(msg);
        response.sendRedirect("index.html");
	}
}


