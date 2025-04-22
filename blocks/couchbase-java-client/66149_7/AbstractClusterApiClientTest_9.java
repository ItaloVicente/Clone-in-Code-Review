package com.couchbase.client.java.cluster.api;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.config.RestApiRequest;
import com.couchbase.client.core.message.config.RestApiResponse;
import com.couchbase.client.core.utils.Blocking;
import com.couchbase.client.deps.io.netty.handler.codec.http.HttpHeaders;
import com.couchbase.client.deps.io.netty.handler.codec.http.HttpMethod;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.json.JsonValue;
import com.couchbase.client.java.env.CouchbaseEnvironment;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class RestBuilder implements IRestBuilder {

    private final AsyncRestBuilder delegate;
    private final long defaultTimeout;
    private final TimeUnit defaultTimeUnit;

    public RestBuilder(AsyncRestBuilder asyncBuilder, long defaultTimeout, TimeUnit defaultTimeUnit) {
        this.delegate = asyncBuilder;
        this.defaultTimeout = defaultTimeout;
        this.defaultTimeUnit = defaultTimeUnit;
    }

    public RestBuilder withParam(String key, String value) {
        delegate.withParam(key, value);
        return this;
    }

    public RestBuilder contentType(String type) {
        delegate.contentType(type);
        return this;
    }

    public RestBuilder withHeader(String key, Object value) {
        delegate.withHeader(key, value);
        return this;
    }

    public RestBuilder body(String jsonBody) {
        delegate.body(jsonBody);
        return this;
    }

    public RestBuilder body(JsonValue jsonBody) {
        delegate.body(jsonBody);
        return this;
    }

    public RestBuilder bodyRaw(String body) {
        delegate.bodyRaw(body);
        return this;
    }

    public RestBuilder accept(String type) {
        delegate.accept(type);
        return this;
    }

    public RestBuilder bodyForm(Form form) {
        delegate.bodyForm(form);
        return this;
    }

    public HttpMethod method() {
        return delegate.method();
    }

    public String path() {
        return delegate.path();
    }

    public Map<String, String> params() {
        return delegate.params();
    }

    public Map<String, Object> headers() {
        return delegate.headers();
    }

    public String body() {
        return delegate.body();
    }

    public RestApiRequest asRequest() {
        return delegate.asRequest();
    }

    public RestApiResponse execute(long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(delegate.execute(), timeout, timeUnit);
    }

    public RestApiResponse execute() {
        return execute(defaultTimeout, defaultTimeUnit);
    }
}
