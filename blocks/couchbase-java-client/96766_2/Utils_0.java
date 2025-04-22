            Span potentialParent = env.tracer().activeSpan();
            if (potentialParent != null && env.propagateParentSpan()) {
                addRequestSpanWithParent(env, potentialParent, request, opName);
                return;
            }

