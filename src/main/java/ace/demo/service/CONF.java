package ace.demo.service;

/**
 * Created by shaobo.asb on 2014/8/19.
 */
public interface CONF {
    final static String accessId = "OHzWscU6fbM3ch1y";
    final static String accessKey = "IzHf74WAbF9z43bO4AZJjBJAiWD7H8";

    /* oss */
    final static String ossBucketname = "ossdemoimage";

    /* rds */
    final static String rdsUrl = "jdbc:mysql://rdsirzvumjyjbiq.mysql.rds.aliyuncs.com:3306/r6me4w6aehl5yxfm";
    final static String rdsUser = "wordpress";
    final static String rdsPasswd = "wordpress";
    final static String rdsDriver = "com.mysql.jdbc.Driver";

    /* mqs */
    final static String mqsQueueName ="uQueue";
    final static  String mqsUrl = "http://apmltv8qxs.mqs-cn-hangzhou.aliyuncs.com";

    /* sls */
    final static String slsProject = "demotest";
    final static String slsCategory = "app_log";
    final static  String slsUrl = "http://sls-cn-hangzhou-intranet.aliyuncs.com";

    /* ocs */

}
