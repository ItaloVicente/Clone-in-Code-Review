
package com.couchbase.client.core.message.config;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.util.Collections;
import java.util.Map;

public class RestApiRequest extends AbstractCouchbaseRequest implements ConfigRequest {

    private final String path;
    private final Map<String, String> queryParameters;
    private final Map<String, Object> headers;
    private final HttpMethod method;
    private final String body;

    public RestApiRequest(String login, String password, HttpMethod method, String path,
      Map<String, String> queryParameters, Map<String, Object> headers, String body) {
        super(login, password);
        this.path = path != null ? path : "";
        this.queryParameters = queryParameters != null ? queryParameters : Collections.<String, String>emptyMap();
        this.headers = headers != null ? headers : Collections.<String, Object>emptyMap();
        this.method = method;
        this.body = body != null ? body : "";
    }

    @Override
    public String path() {
        return this.path;
    }

    public HttpMethod method() {
        return this.method;
    }

    public String body() {
        return this.body;
    }

    public Map<String, String> queryParameters() {
        return this.queryParameters;
    }

    public Map<String, Object> headers() {
        return this.headers;
    }

    public String pathWithParameters() {
        Map<String, String> queryParameters = queryParameters();
        if (queryParameters.isEmpty()) {
            return path();
        }
        StringBuilder pwp = new StringBuilder(path() + "?");
        for (Map.Entry<String, String> queryParam : queryParameters.entrySet()) {
            pwp.append(queryParam.getKey())
                    .append('=')
                    .append(queryParam.getValue())
                    .append('&');
        }
        pwp.deleteCharAt(pwp.length() - 1);
        return pwp.toString();
    }
}
