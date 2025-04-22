
        if (env().tracingEnabled() && msg.span() != null) {
            Scope scope = env().tracer()
                .buildSpan("dispatch_to_server")
                .asChildOf(msg.span())
                .withTag("peer.address", remoteSocket)
                .withTag("local.address", localSocket)
                .startActive(false);
            dispatchSpans.offer(scope.span());
            scope.close();
        }
