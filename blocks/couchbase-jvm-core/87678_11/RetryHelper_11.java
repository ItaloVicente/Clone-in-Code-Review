
            if (environment.tracingEnabled()) {
                Scope scope = environment.tracer().scopeManager().activate(request.span(), false);
                scope.span().log("retry attempt: " + request.retryCount() + 1);
                scope.close();
            }

