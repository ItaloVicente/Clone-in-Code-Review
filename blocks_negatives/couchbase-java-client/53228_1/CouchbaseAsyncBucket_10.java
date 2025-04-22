                        }
                    });
                    final Observable<Boolean> finalSuccess = response.queryStatus().map(new Func1<String, Boolean>() {
                        @Override
                        public Boolean call(String s) {
                            return "success".equalsIgnoreCase(s) || "completed".equalsIgnoreCase(s);
                        }
                    });
                    final Observable<JsonObject> errors = response.errors().map(new Func1<ByteBuf, JsonObject>() {
