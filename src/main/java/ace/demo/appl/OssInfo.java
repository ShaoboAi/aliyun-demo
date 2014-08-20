package ace.demo.appl;

import ace.demo.service.CONF;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shaobo.asb on 2014/8/19.
 */
public class OssInfo extends HttpServlet {
    private static OSSClient client = null;
    public void init(){
        client = new OSSClient(CONF.accessId,CONF.accessKey);
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException	{

        if( client != null) {
            ObjectListing listing = client.listObjects(CONF.ossBucketname);
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                resp.getWriter().println(objectSummary.getKey());
            }

        }else {
            resp.getWriter().println("oss is null");
        }
    }
}
