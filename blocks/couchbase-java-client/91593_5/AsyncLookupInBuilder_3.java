                }).map(new Func1<SimpleSubdocResponse, DocumentFragment<Lookup>>() {
                    @Override
                    public DocumentFragment<Lookup> call(SimpleSubdocResponse response) {
                        try {
                            if (response.status().isSuccess()) {
                                try {
                                    byte[] raw = null;
                                    if (isIncludeRaw()) {
                                        TranscoderUtils.ByteBufToArray rawData = TranscoderUtils.byteBufToByteArray(response.content());
                                        raw = Arrays.copyOfRange(rawData.byteArray, rawData.offset, rawData.offset + rawData.length);
                                    }

                                    Scope decodeScope = null;
                                    if (environment.tracingEnabled()) {
                                        decodeScope = environment.tracer()
                                            .buildSpan("response_decoding")
                                            .asChildOf(response.request().span())
                                            .startActive(true);
                                    }

                                    T content = subdocumentTranscoder.decodeWithMessage(response.content(), fragmentType,
                                        "Couldn't decode subget fragment for " + id + "/" + spec.path());

                                    if (environment.tracingEnabled() && decodeScope != null) {
                                        decodeScope.close();
                                        if (decodeScope.span() instanceof ThresholdLogSpan) {
                                            decodeScope.span().setBaggageItem(ThresholdLogReporter.KEY_DECODE_MICROS,
                                                Long.toString(((ThresholdLogSpan) decodeScope.span()).durationMicros())
                                            );
                                        }
                                    }

                                    SubdocOperationResult<Lookup> single = SubdocOperationResult
                                        .createResult(spec.path(), Lookup.GET, response.status(), content, raw);
                                    return new DocumentFragment<Lookup>(id, response.cas(), response.mutationToken(),
                                        Collections.singletonList(single));
                                } finally {
                                    if (response.content() != null) {
                                        response.content().release();
                                    }
                                }
                            } else {
                                if (response.content() != null && response.content().refCnt() > 0) {
                                    response.content().release();
                                }

                                if (response.status() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
                                    SubdocOperationResult<Lookup> single = SubdocOperationResult
                                        .createResult(spec.path(), Lookup.GET, response.status(), null);
                                    return new DocumentFragment<Lookup>(id, response.cas(), response.mutationToken(), Collections.singletonList(single));
                                } else {
                                    throw SubdocHelper.commonSubdocErrors(response.status(), id, spec.path());
                                }
                            }
                        } finally {
                            if (environment.tracingEnabled()) {
                                environment.tracer().scopeManager()
                                    .activate(response.request().span(), true)
                                    .close();
                            }
                        }
