package com.couchbase.client.http;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

import java.io.IOException;

public class CustomRequestConnControl implements HttpRequestInterceptor {

    private final boolean shouldClose;

    public CustomRequestConnControl(final boolean shouldClose) {
        super();
        this.shouldClose = shouldClose;
    }

    public void process(final HttpRequest request, final HttpContext context)
        throws HttpException, IOException {
        Args.notNull(request, "HTTP request");

        final String method = request.getRequestLine().getMethod();
        if (method.equalsIgnoreCase("CONNECT")) {
            return;
        }

        if (!request.containsHeader(HTTP.CONN_DIRECTIVE)) {
            if (shouldClose) {
                request.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            } else {
                request.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
            }
        }
    }
}
