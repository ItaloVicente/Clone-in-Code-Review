        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        LastHttpContent responseEnd = new DefaultLastHttpContent();
        channel.writeInbound(response, responseEnd);
        QueryHandlerV2.KeepAliveResponse keepAliveResponse = keepAliveRequest.observable()
            .cast(QueryHandlerV2.KeepAliveResponse.class)
            .timeout(1, TimeUnit.SECONDS).toBlocking().single();
