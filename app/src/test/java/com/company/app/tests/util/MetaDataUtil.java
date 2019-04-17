package com.company.app.tests.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This class is used to perform an authorization reqeust to a remote server, using predefined auth data (par of app's business logic)
 */

public class MetaDataUtil {

    public final static String CHARLIE_PUTH_URL_STAGING = "https://dv-api.staging.com/streaming/kpi/artist/1860518";
    public final static String AUTH_HEADER = "Authorization";
    public final static String AUTH_HEADER_VALUE = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHRlbmRlZF9pbmZvIjp7Imxhc3ROYW1lIjoiICJ9LCJhdWQiOlsicGFzc3dvcmQiLCJ1YWEiLCJ1c2VyX2luZm8iLCJvcGVuaWQiLCJ0b2tlbnMiXSwiYWNjb3VudF90eXBlIjoiSU5URVJOQUxfQUQiLCJ1c2VyX25hbWUiOiJudWxsICAiLCJzY29wZSI6W10sImV4cCI6MjExODgyNDA0MSwiZW1haWwiOiJnbGVidUBtYWlsaW5hdG9yLmNvbSIsInVzZXJfcHJvZmlsZV9pZCI6IjRlM2Y2NTBhLWFkYzYtNDAzMy04ZTE1LTQ0ODlmMjA0ZWU0NSIsImp0aSI6Ijg3NTY3MWI1LTJkYTItNGQ1OS1iY2ZlLTdjYTgwYmI0OGExMCIsImNsaWVudF9pZCI6IkFydGlzdENoYXJ0c01vYmlsZSJ9.2RL_swJoPfn34B-B0NvUb0m58CoHtcmDfGhWKPNp6WI";

    private static JSONObject testMetadata;

    public static String getWeekPerformance() {
        try {
            return NumberFormatterUtil.formatValue(Double.valueOf(testMetadata.getJSONObject("data").getString("count_this_week_to_date")));
        } catch (JSONException e) {
            return "failed to retrieve WeekPerformance!";
        }
    }

    public static String getOverallPerformance() {
        try {
            return NumberFormatterUtil.formatValue(Double.valueOf(testMetadata.getJSONObject("data").getString("count_rtd")));
        } catch (JSONException e) {
            return "failed to retrieve OverallPerformance!";
        }
    }

    public static void updateMetadata() throws IOException, JSONException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(MetaDataUtil.CHARLIE_PUTH_URL_STAGING);
        request.addHeader(MetaDataUtil.AUTH_HEADER, MetaDataUtil.AUTH_HEADER_VALUE);
        HttpResponse response = client.execute(request);
        String jsonString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        JSONArray jsonArray = new JSONArray(jsonString);
        testMetadata = (JSONObject) jsonArray.get(0);
    }
}