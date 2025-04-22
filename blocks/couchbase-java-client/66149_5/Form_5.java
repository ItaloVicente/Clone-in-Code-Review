package com.couchbase.client.java.cluster.api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.config.RestApiRequest;
import com.couchbase.client.core.message.config.RestApiResponse;
import com.couchbase.client.core.utils.Blocking;
import com.couchbase.client.deps.io.netty.handler.codec.http.HttpHeaders;
import com.couchbase.client.deps.io.netty.handler.codec.http.HttpMethod;
import com.couchbase.client.deps.io.netty.handler.timeout.TimeoutException;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.json.JsonValue;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.query.core.N1qlQueryExecutor;
import rx.Observable;
import rx.functions.Func0;

public class ClusterApiClient {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(N1qlQueryExecutor.class);
    private static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.MILLISECONDS;

    private final String login;
    private final String password;
    private final ClusterFacade core;
    private final CouchbaseEnvironment environment;
    private final long defaultTimeout;

    public ClusterApiClient(String username, String password, ClusterFacade core,
            CouchbaseEnvironment environment) {
        this.login = username;
        this.password = password;
        this.core = core;
        this.environment = environment;
        this.defaultTimeout = environment.viewTimeout();
    }

    public RestBuilder get(String... paths) {
        return new RestBuilder(HttpMethod.GET, buildPath(paths));
    }

    public RestBuilder post(String... paths) {
        return new RestBuilder(HttpMethod.POST, buildPath(paths));
    }

    public RestBuilder put(String... paths) {
        return new RestBuilder(HttpMethod.PUT, buildPath(paths));
    }

    public RestBuilder delete(String... paths) {
        return new RestBuilder(HttpMethod.DELETE, buildPath(paths));
    }

    public static String buildPath(String... paths) {
        if (paths == null || paths.length == 0) {
            throw new IllegalArgumentException();
        }

        StringBuilder path = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            String p = paths[i];
            if (p == null) continue;

            if (p.charAt(0) != '/') {
                path.append('/');
            }
            if (p.charAt(p.length() - 1) == '/') {
                path.append(p, 0, p.length() - 1);
            } else {
                path.append(p);
            }
        }
        return path.toString();
    }

    public class RestBuilder {

        private final HttpMethod method;
        private final String path;

        private final Map<String, String> params;
        private final Map<String, Object> headers;

        private String body;

        public RestBuilder(HttpMethod method, String path) {
            this.method = method;
            this.path = path;
            this.body = "";

            this.params = new LinkedHashMap<String, String>();
            this.headers = new LinkedHashMap<String, Object>();
        }

        public RestBuilder withParam(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        public RestBuilder withHeader(String key, Object value) {
            this.headers.put(key, value);
            return this;
        }

        public RestBuilder bodyRaw(String body) {
            this.body = body;
            return this;
        }

        public RestBuilder contentType(String type) {
            return withHeader(HttpHeaders.Names.CONTENT_TYPE, type);
        }

        public RestBuilder accept(String type) {
            return withHeader(HttpHeaders.Names.ACCEPT, type);
        }

        public RestBuilder body(String jsonBody) {
            accept(HttpHeaders.Values.APPLICATION_JSON);
            contentType(HttpHeaders.Values.APPLICATION_JSON);
            bodyRaw(jsonBody);
            return this;
        }

        public RestBuilder body(JsonValue jsonBody) {
            return body(jsonBody.toString());
        }

        public RestBuilder bodyForm(Form form) {
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
            return new RestApiRequest(
                    ClusterApiClient.this.login, ClusterApiClient.this.password,
                    this.method, this.path, this.params, this.headers, this.body
            );
        }

        public Observable<RestApiResponse> executeAsync() {
            return Observable.defer(new Func0<Observable<RestApiResponse>>() {
                @Override
                public Observable<RestApiResponse> call() {
                    RestApiRequest apiRequest = asRequest();
                    LOGGER.debug("Executing Cluster API request {} on {}", apiRequest.method(), apiRequest.pathWithParameters());
                    return core.send(asRequest());
                }
            });
        }

        public RestApiResponse execute(long timeout, TimeUnit timeUnit) {
            return Blocking.blockForSingle(executeAsync(), timeout, timeUnit);
        }

        public RestApiResponse execute() {
            return execute(defaultTimeout, DEFAULT_TIMEUNIT);
        }
    }

}
