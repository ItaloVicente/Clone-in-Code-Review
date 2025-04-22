        if (env().tracingEnabled() && msg.span() != null) {
            Scope scope = env().tracer().buildSpan("dispatch_to_server").asChildOf(msg.span())
                .withTag("local.address", localSocketName)
                .withTag("remote.address", remoteSocketName)
                .startActive(false);
            dispatchSpan = scope.span();
        }
