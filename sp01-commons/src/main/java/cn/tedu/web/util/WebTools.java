package cn.tedu.web.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
 
public class WebTools {
 
    public static String KEY_STATUS_CODE = "statusCode";
    public static String KEY_CONTENT = "content";
 
    private final static PoolingHttpClientConnectionManager poolConnManager = new PoolingHttpClientConnectionManager();  //连接池管理器
    private final static HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {  //retry handler
        @Override
        public boolean retryRequest(IOException exception,
                                    int executionCount, HttpContext context) {
            if (executionCount >= 5) {
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                return true;
            }
            if (exception instanceof InterruptedIOException) {
                return false;
            }
            if (exception instanceof UnknownHostException) {
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                return false;
            }
            HttpClientContext clientContext = HttpClientContext
                    .adapt(context);
            HttpRequest request = clientContext.getRequest();
 
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        }
    };
 
    static {   //类加载的时候 设置最大连接数 和 每个路由的最大连接数
        poolConnManager.setMaxTotal(2000); 
        poolConnManager.setDefaultMaxPerRoute(1000);
    }
 
 
    /**
     * ########################### core code#######################
     * @return
     */
    private static CloseableHttpClient getCloseableHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(poolConnManager)
                .setRetryHandler(httpRequestRetryHandler)
                .build();
 
        return httpClient;
    }
 
    /**
     * buildResultMap
     *
     * @param response
     * @param entity
     * @return
     * @throws IOException
     */
    private static Map<String, Object> buildResultMap(CloseableHttpResponse response, HttpEntity entity) throws
            IOException {
        Map<String, Object> result;
        result = new HashMap<>(2);
        result.put(KEY_STATUS_CODE, response.getStatusLine().getStatusCode());  //status code
        if (entity != null) {
            result.put(KEY_CONTENT, EntityUtils.toString(entity, "UTF-8")); //message content
        }
        return result;
    }
 
    /**
     * send json by post method
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static Map<String, Object> postJson(String url, JSONObject json) {
 
        Map<String, Object> result = null;
        CloseableHttpClient httpClient = getCloseableHttpClient();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
 
            httpPost.setHeader("Accept", "application/json;charset=UTF-8");
            httpPost.setHeader("Content-Type", "application/json");
 
            StringEntity stringEntity = new StringEntity(json.toString(), "utf-8");
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = buildResultMap(response, entity);
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject.put("username","test");
        jsonObject.put("password","test_on");
        jsonObject.put("serviceName","carInfo");
        jsonObject2.put("carNumber","豫KGX232");
        jsonObject2.put("type","02");
        jsonObject.put("param",jsonObject2);
        Map<String, Object> stringObjectMap = WebTools.postJson("http://tk-driving.chinadatatrading.com/api/getInfoCarNumber",
                jsonObject);
        System.out.println(stringObjectMap);
    }

}