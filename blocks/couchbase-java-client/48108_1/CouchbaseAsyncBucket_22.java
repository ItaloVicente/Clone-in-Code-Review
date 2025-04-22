                        }
                    }).reduce(new ArrayList<Throwable>(),
                            new Func2<ArrayList<Throwable>, Exception, ArrayList<Throwable>>() {
                                @Override
                                public ArrayList<Throwable> call(ArrayList<Throwable> throwables,
                                        Exception error) {
                                    throwables.add(error);
                                    return throwables;
