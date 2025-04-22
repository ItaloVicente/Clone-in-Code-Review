    private static void assertTryUntil(Func0<Boolean> callback) {
        int maxIters = 50; // 50 * 100ms == Max 5000ms
        int iters = 0;
        while (true) {
            if (callback.call()) {
                assertTrue(true);
                break;
            } else {
                if (iters++ <= maxIters) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    assertTrue(false);
                }
            }
        }
    }
