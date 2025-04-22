

    private static <T> Func1<List<JsonObject>, Observable<T>> errorsToThrowable(final String messagePrefix) {
        return new Func1<List<JsonObject>, Observable<T>>() {
            @Override
            public Observable<T> call(List<JsonObject> errors) {
                return Observable.<T>error(new CouchbaseException(messagePrefix + errors));
            }
        };
    }

    private static Func1<AsyncN1qlQueryRow, IndexInfo> ROW_VALUE_TO_INDEXINFO =
            new Func1<AsyncN1qlQueryRow, IndexInfo>() {
                @Override
                public IndexInfo call(AsyncN1qlQueryRow asyncN1qlQueryRow) {
                    return new IndexInfo(asyncN1qlQueryRow.value());
                }
            };

    @Override
    public Observable<IndexInfo> listIndexes() {
        Statement listIndexes = select("idx.*").from(x("system:indexes").as("idx")).where(x("keyspace_id").eq(s(bucket)))
                .orderBy(Sort.desc("is_primary"), Sort.asc("name"));

        final Func1<List<JsonObject>, Observable<AsyncN1qlQueryRow>> errorHandler = errorsToThrowable(
                "Error while listing indexes: ");

        return queryExecutor.execute(
                N1qlQuery.simple(listIndexes, N1qlParams.build().consistency(ScanConsistency.REQUEST_PLUS)))
                            .flatMap(new Func1<AsyncN1qlQueryResult, Observable<AsyncN1qlQueryRow>>() {
                                @Override
                                public Observable<AsyncN1qlQueryRow> call(final AsyncN1qlQueryResult aqr) {
                                    return aqr.finalSuccess()
                                            .flatMap(new Func1<Boolean, Observable<AsyncN1qlQueryRow>>() {
                                                @Override
                                                public Observable<AsyncN1qlQueryRow> call(Boolean success) {
                                                    if (success) {
                                                        return aqr.rows();
                                                    } else {
                                                        return aqr.errors().toList().flatMap(errorHandler);
                                                    }
                                                }
                                            });
                                }
                            }).map(ROW_VALUE_TO_INDEXINFO);
    }

    @Override
    public Observable<Boolean> createPrimaryIndex(final boolean ignoreIfExist, boolean defer) {
        Statement createIndex;
        UsingWithPath usingWithPath = Index.createPrimaryIndex().on(bucket);
        if (defer) {
            createIndex = usingWithPath.withDefer();
        } else {
            createIndex = usingWithPath;
        }

        return queryExecutor.execute(N1qlQuery.simple(createIndex))
            .flatMap(new Func1<AsyncN1qlQueryResult, Observable<Boolean>>() {
                @Override
                public Observable<Boolean> call(final AsyncN1qlQueryResult aqr) {
                    return aqr.finalSuccess()
                              .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                                  @Override
                                  public Observable<Boolean> call(Boolean success) {
                                      if (success) {
                                          return Observable.just(true);
                                      } else {
                                          return aqr.errors().toList()
                                                    .flatMap(new Func1<List<JsonObject>, Observable<Boolean>>() {
                                                        @Override
                                                        public Observable<Boolean> call(List<JsonObject> errors) {
                                                            if (ignoreIfExist && errors.size() == 1
                                                                    && errors.get(0)
                                                                             .getString("msg")
                                                                             .contains("already exist")) {
                                                                return Observable.just(false);
                                                            } else {
                                                                return Observable.error(new CouchbaseException(
                                                                        "Error creating primary index: " + errors));
                                                            }
                                                        }
                                                    });
                                      }
                                  }
                              });
                }
            });
    }

    private static Expression expressionOrIdentifier(Object o) {
        if (o instanceof Expression) {
            return (Expression) o;
        } else if (o instanceof String) {
            return i((String) o);
        } else {
            throw new IllegalArgumentException("Fields for index must be either an Expression or a String identifier");
        }
    }

    @Override
    public Observable<Boolean> createIndex(final String indexName, final boolean ignoreIfExist, boolean defer, Object... fields) {
        if (fields == null || fields.length < 1) {
            throw new IllegalArgumentException("At least one field is required for secondary index");
        }

        Expression firstExpression = expressionOrIdentifier(fields[0]);
        Expression[] otherExpressions = new Expression[fields.length - 1];
        for (int i = 1; i < fields.length; i++) {
            otherExpressions[i - 1] = expressionOrIdentifier(fields[i]);
        }

        Statement createIndex;
        UsingWithPath usingWithPath = Index.createIndex(indexName).on(bucket, firstExpression, otherExpressions);
        if (defer) {
            createIndex = usingWithPath.withDefer();
        } else {
            createIndex = usingWithPath;
        }

        return queryExecutor.execute(N1qlQuery.simple(createIndex))
                .compose(checkIndexCreation(ignoreIfExist, "Error creating secondary index " + indexName));
    }


    @Override
    public Observable<Boolean> dropPrimaryIndex(final boolean ignoreIfNotExist) {
        return drop(ignoreIfNotExist, Index.dropPrimaryIndex(bucket).using(IndexType.GSI), "Error dropping primary index: ");
    }

    @Override
    public Observable<Boolean> dropIndex(String name, boolean ignoreIfNotExist) {
        return drop(ignoreIfNotExist, Index.dropIndex(bucket, name).using(IndexType.GSI), "Error dropping index \"" + name + "\"");
    }

    private Observable<Boolean> drop(final boolean ignoreIfNotExist, Statement dropIndex, final String errorPrefix) {
        return queryExecutor.execute(N1qlQuery.simple(dropIndex))
                .flatMap(new Func1<AsyncN1qlQueryResult, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(final AsyncN1qlQueryResult aqr) {
                        return aqr.finalSuccess()
                                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                                    @Override
                                    public Observable<Boolean> call(Boolean success) {
                                        if (success) {
                                            return Observable.just(true);
                                        } else {
                                            return aqr.errors().toList()
                                                .flatMap(new Func1<List<JsonObject>, Observable<Boolean>>() {
                                                    @Override
                                                    public Observable<Boolean> call(List<JsonObject> errors) {
                                                        if (ignoreIfNotExist && errors.size() == 1
                                                                && errors.get(0).getString("msg").contains("not found")) {
                                                            return Observable.just(false);
                                                        } else {
                                                            return Observable.error(new CouchbaseException(errorPrefix + errors));
                                                        }
                                                    }
                                                });
                                        }
                                    }
                                });
                    }
                });
    }


    @Override
    public Observable<List<String>> buildDeferredIndexes() {

        final Func1<List<JsonObject>, Observable<List<String>>> errorHandler = errorsToThrowable(
                "Error while triggering index build: ");

        return listIndexes()
                .filter(new Func1<IndexInfo, Boolean>() {
                    @Override
                    public Boolean call(IndexInfo indexInfo) {
                        return indexInfo.state().equals("pending");
                    }
                })
                .map(new Func1<IndexInfo, String>() {
                    @Override
                    public String call(IndexInfo indexInfo) {
                        return indexInfo.name();
                    }
                })
                .toList()
                .flatMap(new Func1<List<String>, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(final List<String> pendingIndexes) {
                        if (pendingIndexes.isEmpty()) {
                            return Observable.just(pendingIndexes);
                        }
                        Statement buildStatement = Index.buildIndex().on(bucket)
                                .indexes(pendingIndexes)
                                .using(IndexType.GSI);

                        return queryExecutor.execute(N1qlQuery.simple(buildStatement))
                                .flatMap(new Func1<AsyncN1qlQueryResult, Observable<List<String>>>() {
                                    @Override
                                    public Observable<List<String>> call(final AsyncN1qlQueryResult aqr) {
                                        return aqr.finalSuccess()
                                                .flatMap(new Func1<Boolean, Observable<List<String>>>() {
                                                    @Override
                                                    public Observable<List<String>> call(
                                                            Boolean success) {
                                                        if (success) {
                                                            return Observable.just(pendingIndexes);
                                                        } else {
                                                            return aqr.errors().toList().flatMap(errorHandler);
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    @Override
    public Observable<IndexInfo> watchIndex(final String indexName, long watchTimeout, TimeUnit watchTimeUnit) {
        return listIndexes()
                .flatMap(new Func1<IndexInfo, Observable<IndexInfo>>() {
                    @Override
                    public Observable<IndexInfo> call(IndexInfo i) {
                        if (!indexName.equals(i.name())) {
                            return Observable.empty();
                        } else if (!"online".equals(i.state()))
                            return Observable.error(new IndexesNotReadyException("Index not ready: " + i.name()));
                        else {
                            return Observable.just(i);
                        }
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable e) {
                        if (e instanceof IndexesNotReadyException) {
                            System.out.println(e.getMessage());
                        }
                    }
                })
                .retryWhen(anyOf(IndexesNotReadyException.class)
                        .delay(Delay.linear(TimeUnit.MILLISECONDS, 1000, 50, 500))
                        .max(100)
                        .build())
                .compose(safeAbort(watchTimeout, watchTimeUnit));
    }

    @Override
    public Observable<IndexInfo> watchIndexes(boolean watchPrimary, long watchTimeout, TimeUnit watchTimeUnit,
            String... watchList) {
        if (watchList == null || watchList.length == 0) {
            return watchIndexes(watchPrimary, watchTimeout, watchTimeUnit, Collections.<String>emptyList());
        }
        return watchIndexes(watchPrimary, watchTimeout, watchTimeUnit, Arrays.asList(watchList));
    }

    @Override
    public Observable<IndexInfo> watchIndexes(boolean watchPrimary, final long watchTimeout,
            final TimeUnit watchTimeUnit,
            List<String> watchList) {

        return Observable.from(watchList)
                .flatMap(new Func1<String, Observable<IndexInfo>>() {
                    @Override
                    public Observable<IndexInfo> call(String s) {
                        return watchIndex(s, watchTimeout, watchTimeUnit);
                    }
                })
                .compose(safeAbort(watchTimeout, watchTimeUnit));
    }

    private static Observable.Transformer<IndexInfo, IndexInfo> safeAbort(final long watchTimeout, final TimeUnit watchTimeUnit) {
        return new Observable.Transformer<IndexInfo, IndexInfo>() {
            @Override
            public Observable<IndexInfo> call(Observable<IndexInfo> source) {
                return source.timeout(watchTimeout, watchTimeUnit)
                .onErrorResumeNext(new Func1<Throwable, rx.Observable<IndexInfo>>() {
                    @Override
                    public rx.Observable<IndexInfo> call(Throwable t) {
                        if (t instanceof IndexesNotReadyException || t instanceof TimeoutException) {
                            return Observable.empty();
                        }
                        return rx.Observable.error(t);
                    }
                });
            }
        };
    }

    private static Observable.Transformer<AsyncN1qlQueryResult, Boolean> checkIndexCreation(final boolean ignoreIfExist,
            final String prefixMsg) {
        return new Observable.Transformer<AsyncN1qlQueryResult, Boolean>() {
            @Override
            public Observable<Boolean> call(Observable<AsyncN1qlQueryResult> sourceObservable) {
                return sourceObservable
                        .flatMap(new Func1<AsyncN1qlQueryResult, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(final AsyncN1qlQueryResult aqr) {
                                return aqr.finalSuccess()
                                        .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                                            @Override
                                            public Observable<Boolean> call(Boolean success) {
                                                if (success) {
                                                    return Observable.just(true);
                                                } else {
                                                    return aqr.errors()
                                                    .toList()
                                                    .flatMap(new Func1<List<JsonObject>, Observable<Boolean>>() {
                                                        @Override
                                                        public Observable<Boolean> call(
                                                                List<JsonObject> errors) {
                                                            if (ignoreIfExist && errors.size() == 1
                                                                    && errors.get(0).getString("msg").contains("already exist")) {
                                                                return Observable.just(false);
                                                            } else {
                                                                return Observable.error(new CouchbaseException(prefixMsg + ": " + errors));
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            }
                        });
            }
        };
    }
