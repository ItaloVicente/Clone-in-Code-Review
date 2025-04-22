                        Span requestSpan = null;
                        if (env.tracingEnabled()) {
                            Scope scope = env.tracer()
                                .buildSpan("upsert")
                                .startActive(false);
                            requestSpan = scope.span();
                            scope.close();
                        }

                        Scope encodeScope = null;
                        if (requestSpan != null) {
                            encodeScope = env.tracer()
                                .buildSpan("request_encoding")
                                .asChildOf(requestSpan)
                                .startActive(true);
                        }

                        Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);

                        if (encodeScope != null) {
                            encodeScope.close();
                            if (encodeScope.span() instanceof ThresholdLogSpan) {
                                encodeScope.span().setBaggageItem(ThresholdLogReporter.KEY_ENCODE_MICROS,
                                    Long.toString(((ThresholdLogSpan) encodeScope.span()).durationMicros())
                                );
                            }
                        }

                        UpsertRequest request = new UpsertRequest(
                            document.id(), encoded.value1(), document.expiry(), encoded.value2(), bucket
                        );
                        r.set(request);
                        if (requestSpan != null) {
                            request.span(requestSpan, env);
                        }
