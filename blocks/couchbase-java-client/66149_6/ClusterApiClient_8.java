package com.couchbase.client.java.cluster.api;

import java.util.LinkedHashMap;
import java.util.Map;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.config.RestApiRequest;
import com.couchbase.client.core.message.config.RestApiResponse;
import com.couchbase.client.deps.io.netty.handler.codec.http.HttpHeaders;
import com.couchbase.client.deps.io.netty.handler.codec.http.HttpMethod;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.json.JsonValue;
import com.couchbase.client.java.query.core.N1qlQueryExecutor;
import rx.Observable;
import rx.functions.Func0;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class AsyncRestBuilder implements IRestBuilder {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(N1qlQueryExecutor.class);

    private final String username;
    private final String password;
    private final ClusterFacade core;

    private final HttpMethod method;
    private final String path;
    private final Map<String, String> params;
    private final Map<String, Object> headers;
    private String body;

    public AsyncRestBuilder(String username, String password, ClusterFacade core, HttpMethod method, String path) {
        this.username = username;
        this.password = password;
        this.core = core;

        this.method = method;
        this.path = path;
        this.body = "";

        this.params = new LinkedHashMap<String, String>();
        this.headers = new LinkedHashMap<String, Object>();
    }

    public AsyncRestBuilder withParam(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public AsyncRestBuilder withHeader(String key, Object value) {
        this.headers.put(key, value);
        return this;
    }

    public AsyncRestBuilder bodyRaw(String body) {
        this.body = body;
        return this;
    }

    public AsyncRestBuilder contentType(String type) {
        return withHeader(HttpHeaders.Names.CONTENT_TYPE, type);
    }

    public AsyncRestBuilder accept(String type) {
        return withHeader(HttpHeaders.Names.ACCEPT, type);
    }

    public AsyncRestBuilder body(String jsonBody) {
        accept(HttpHeaders.Values.APPLICATION_JSON);
        contentType(HttpHeaders.Values.APPLICATION_JSON);
        bodyRaw(jsonBody);
        return this;
    }

    public AsyncRestBuilder body(JsonValue jsonBody) {
        return body(jsonBody.toString());
    }

    public AsyncRestBuilder bodyForm(Form form) {
        contentType(HttpHeaders.Values.APPLICATION_X_WWW_FORM_URLENCODED);
        return bodyRaw(form.toUrlEncodedString());
    }


    public HttpMethod method() {
        return method;
    }

    public String path() {
        return this.path;
    }

    public String body() {
        return this.body;
    }

    public Map<String, String> params() {
        return new LinkedHashMap<String, String>(params);
    }

    public Map<String, Object> headers() {
        return new LinkedHashMap<String, Object>(headers);
    }


    public RestApiRequest asRequest() {
        return new RestApiRequest(this.username, this.password,
                this.method, this.path, this.params, this.headers, this.body);
    }

    public Observable<RestApiResponse> execute() {
        return Observable.defer(new Func0<Observable<RestApiResponse>>() {
            @Override
            public Observable<RestApiResponse> call() {
                RestApiRequest apiRequest = asRequest();
                LOGGER.debug("Executing Cluster API request {} on {}", apiRequest.method(), apiRequest.pathWithParameters());
                return core.send(asRequest());
            }
        });
    }

}
