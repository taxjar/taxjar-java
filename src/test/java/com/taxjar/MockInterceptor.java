package com.taxjar;

import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

public class MockInterceptor implements Interceptor {
    private String responsePath;
    private Map<String, Object> responseOpts;

    public MockInterceptor(String path) {
        responsePath = path;
    }

    public MockInterceptor(String path, Map<String, Object> opts) {
        responsePath = path;
        responseOpts = opts;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        String responseString;
        Integer responseCode = 200;

        final URI uri = chain.request().url().uri();

        if (!responsePath.isEmpty()) {
            responseString = readFile(responsePath);
        } else {
            responseString = "";
        }

        if (responseOpts != null && responseOpts.containsKey("code")) {
            responseCode = (Integer) responseOpts.get("code");
        }

        response = new Response.Builder()
                .code(responseCode)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();

        return response;
    }

    protected String readFile(String path) throws IOException {
        InputStream resource = getClass().getResourceAsStream("/com/taxjar/fixtures/" + path);
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buf = new byte [1024];

        try {
            for (int i = resource.read(buf); i > 0; i = resource.read(buf)) {
                os.write(buf, 0, i);
            }
        } catch (RuntimeException e) {
            // No-op
        }

        return os.toString("utf8");
    }

}
