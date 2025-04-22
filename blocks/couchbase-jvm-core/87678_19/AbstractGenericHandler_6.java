
            Span dispatchSpan = this.dispatchSpan.poll();
            if (dispatchSpan != null) {
                Scope scope = env().tracer().scopeManager().activate(dispatchSpan, true);
                scope.span()
                    .setTag("local.address", localSocketName)
                    .setTag("remote.address", remoteSocketName);
                scope.close();
            }
