
        if (env().tracingEnabled() && msg.span() != null) {
            Scope scope = env().tracer()
                .buildSpan("dispatch_to_server")
                .asChildOf(msg.span())
                .withTag("peer.address", remoteSocket)
                .withTag("local.address", localSocket)
                .withTag("local.id", localId)
                .startActive(false);
            if (msg.span() instanceof ThresholdLogSpan) {
                msg.span()
                    .setBaggageItem("peer.address", remoteSocket)
                    .setBaggageItem("local.address", localSocket)
                    .setBaggageItem("local.id", localId);
            }
            dispatchSpans.offer(scope.span());
            scope.close();
        }
