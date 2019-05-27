package huan.yan.myproject.zabbix;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.hengyunabc.zabbix.api.DefaultZabbixApi;
import io.github.hengyunabc.zabbix.api.Request;
import io.github.hengyunabc.zabbix.api.RequestBuilder;
import org.springframework.util.StringUtils;

public class ZabbixTest {

    public static void main(String[] args) {

        DefaultZabbixApi zabbixApi = new DefaultZabbixApi("http://10.50.4.59/zabbix/api_jsonrpc.php");
        zabbixApi.init();
        try {
            boolean loginResult = zabbixApi.login("Admin", "zabbix");
            if (!loginResult) {
                System.out.println("login fail");
            }
            //查询所有主机
//            Request request = RequestBuilder.newBuilder().method("hostgroup.get").paramEntry("output", "extend").build();
            Request request = RequestBuilder.newBuilder().method("hostgroup.get").paramEntry("output", "extend").build();
            //执行请求
            JSONObject resJson = zabbixApi.call(request);
            //处理结果
            String error = String.valueOf(resJson.get("error"));
            if (!StringUtils.isEmpty(error) && error != "null") {
                System.out.println("调用zabbix接口出错");
            } else {
                JSONArray jsonArray = resJson.getJSONArray("result");
                String resultStr = jsonArray.toJSONString();
                System.out.println("结果：：：：：" + resultStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zabbixApi != null) {
                zabbixApi.destroy();
            }
        }
    }


}
