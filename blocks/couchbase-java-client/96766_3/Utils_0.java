            if (env.propagateParentSpan()) {
                Span potentialParent = env.tracer().activeSpan();
                if (potentialParent != null) {
                    addRequestSpanWithParent(env, potentialParent, request, opName);
                    return;
                }
            }

