				.submit(() -> {
                                    long start = System.currentTimeMillis();
                                    System.out.println("starting gc");
                                    latch.countDown();
                                    Collection<PackFile> r = gc.gc();
                                    System.out.println("gc took "
                                            + (System.currentTimeMillis() - start) + " ms");
                                    return r;
                });
