package ace.demo.appl;

import ace.demo.service.Comments;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RdsInfo extends HttpServlet {
    private  Comments comment = null;
    @Override
    public void init(){
        comment = new Comments();
    }
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException	{

    	List<String> bloglist = comment.get();
		for (String data : bloglist) {
			resp.getWriter().println(data);
		}
		resp.getWriter().println("db service.");
	}
}


