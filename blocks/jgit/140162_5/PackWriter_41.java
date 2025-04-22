				executor.execute(() -> {
                                    try {
                                        task.call();
                                    } catch (Throwable failure) {
                                        errors.add(failure);
                                    }
                                });
