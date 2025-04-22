                GenericQueryRequest request = createN1qlRequest(query, bucket, username, password, null);
                Utils.addRequestSpan(env, request, "n1ql");
                if (env.tracingEnabled()) {
                    request.span().setTag(Tags.DB_STATEMENT.getKey(), query.statement().toString());
                }
                return applyTimeout(core.<GenericQueryResponse>send(request), request, env, timeout, timeUnit);
