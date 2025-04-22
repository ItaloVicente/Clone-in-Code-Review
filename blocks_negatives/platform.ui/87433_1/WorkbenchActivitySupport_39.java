                                            new Runnable() {

                                                @Override
												public void run() {
                                                    BusyIndicator
                                                            .showWhile(
                                                                    workbench
                                                                            .getDisplay(),
                                                                    new Runnable() {

                                                                        @Override
																		public void run() {
                                                                            try {
                                                                                dialog
                                                                                        .run(
                                                                                                false,
                                                                                                false,
                                                                                                runnable);
                                                                            } catch (InvocationTargetException e) {
                                                                                log(e);
                                                                            } catch (InterruptedException e) {
                                                                                log(e);
                                                                            }
                                                                        }
                                                                    });
                                                }
                                            });
