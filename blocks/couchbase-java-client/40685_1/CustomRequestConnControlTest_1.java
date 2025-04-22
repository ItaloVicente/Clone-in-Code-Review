package com.couchbase.client.http;

import org.apache.http.HttpRequest;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.protocol.HTTP;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomRequestConnControlTest {

    @Test
    public void shouldSetKeepAlive() throws Exception {
        HttpRequest request = mock(HttpRequest.class);
        when(request.getRequestLine()).thenReturn(new BasicRequestLine("GET", "/foo",
            new ProtocolVersion("HTTP", 1, 1)));

        CustomRequestConnControl processor = new CustomRequestConnControl(false);
        processor.process(request, null);

        verify(request, times(1)).addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
    }

    @Test
    public void shouldSetClose() throws Exception {
        HttpRequest request = mock(HttpRequest.class);
        when(request.getRequestLine()).thenReturn(new BasicRequestLine("GET", "/foo",
            new ProtocolVersion("HTTP", 1, 1)));

        CustomRequestConnControl processor = new CustomRequestConnControl(true);
        processor.process(request, null);

        verify(request, times(1)).addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
    }

}
