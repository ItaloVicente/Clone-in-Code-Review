                GenericAnalyticsRequest request = GenericAnalyticsRequest
                    .jsonQuery(query.query().toString(), bucket, username, password);
                Utils.addRequestSpan(env, request, "analytics");
                if (env.tracingEnabled()) {
                    request.span().setTag(Tags.DB_STATEMENT.getKey(), query.statement());
                }
                return applyTimeout(core.<GenericAnalyticsResponse>send(request), request, env, timeout, timeUnit);
