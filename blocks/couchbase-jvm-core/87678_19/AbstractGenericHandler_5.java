        if (env().tracingEnabled() && msg.span() != null) {
            Scope scope = env().tracer().buildSpan("dispatch_to_server").asChildOf(msg.span())
                .withTag("component", channelId)
                .startActive(false);
            dispatchSpan.offer(scope.span());
            SpanContext cx = scope.span().context();
            if (cx instanceof SlowOperationSpanContext) {
                ((SlowOperationSpanContext) cx).remoteSocket(remoteSocketName);
            }
        }
