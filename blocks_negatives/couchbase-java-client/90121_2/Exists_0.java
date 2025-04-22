                request.subscriber(s);
                Observable<Boolean> result = core.<ObserveResponse>send(request)
                    .map(new Func1<ObserveResponse, Boolean>() {
                        @Override
                        public Boolean call(ObserveResponse response) {
                            ByteBuf content = response.content();
                            if (content != null && content.refCnt() > 0) {
                                content.release();
                            }

                            if (environment.tracingEnabled()) {
                                environment.tracer().scopeManager()
                                    .activate(response.request().span(), true)
                                    .close();
                            }
