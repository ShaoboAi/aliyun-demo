package ace.demo.appl;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ace.demo.service.CONF;
import ace.demo.service.Comments;
import com.aliyun.mqs.client.MQSClient;
import com.aliyun.openservices.sls.LogItem;
import com.aliyun.openservices.sls.SLSClient;
import com.aliyun.openservices.sls.SlsException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.aliyun.mqs.client.CloudQueue;
import com.aliyun.mqs.client.DefaultMQSClient;
import com.aliyun.mqs.model.Message;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadServlet extends  HttpServlet {
    private Comments comments   = null;
	private  static OSSClient ossClient  = null;
	//private  static SLSClient slsClient  = null;
    private  static MQSClient mqsClient = null;
    private  static Logger logger = LoggerFactory.getLogger(UploadServlet.class);
    public UploadServlet() {
    	super();
    }

	public void init() throws ServletException {
        super.init();
        // sls = new SLSClient(accessId, accessKey, "http://sls-cn-hangzhou-intranet.aliyuncs.com");
        comments = new Comments();
        ossClient = new OSSClient( CONF.accessId,CONF.accessKey);
        mqsClient = new DefaultMQSClient(CONF.mqsUrl, CONF.accessId, CONF.accessKey);
        //slsClient = new SLSClient(CONF.accessId, CONF.accessKey, CONF.slsUrl);
     }

	public void destroy() {

	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fileName = null;
    	DiskFileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload sfu=new ServletFileUpload(factory);
        sfu.setSizeMax(1024*1024);
        try {
			List<FileItem> fileItems= sfu.parseRequest(request);
			for (FileItem fi : fileItems) {
				if( fi.isFormField() ) continue;
                fileName =fi.getName();
                ObjectMetadata meta = new ObjectMetadata();
                meta.setContentLength(fi.getSize());
                ossClient.putObject(CONF.ossBucketname, fileName, fi.getInputStream(), meta);
			}
		} catch (FileUploadException e) {
			logger.error(e.getMessage());
		}
		CloudQueue queue = mqsClient.getQueueRef(CONF.mqsQueueName);
		Message msg = new Message();
		msg.setMessageBody(fileName);
		queue.putMessage(msg);
        comments.put(fileName);
        response.sendRedirect("index.html");
//        for (int i = 0; i < 0; i++) {
//        	Vector<LogItem> logGroup = new Vector<LogItem>();
//        	LogItem logItem = new LogItem((int) (new Date().getTime() / 1000));
//        	logItem.PushBack("level", "info");
//        	logItem.PushBack("name", String.valueOf(i));
//        	logItem.PushBack("message", "it's a test message");
//        	logGroup.add(logItem);
//        	try {
//        		slsClient.PutData(CONF.slsProject, CONF.slsCategory, "sls_topic_1", logGroup);
//        	} catch (SlsException e) {
//                //LOG
//                logger.error(e.getMessage());
//        		return;
//        	}
//        }

	}
}


