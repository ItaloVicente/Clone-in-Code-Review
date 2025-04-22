                                            () -> BusyIndicator
											        .showWhile(
											                workbench
											                        .getDisplay(),
											                () -> {
															    try {
															        dialog
															                .run(
															                        false,
															                        false,
															                        runnable);
															    } catch (InvocationTargetException e1) {
															        log(e1);
															    } catch (InterruptedException e2) {
															        log(e2);
															    }
															}));
