        runEventLoop();
        return super.isCanceled();
    }

    /**
     * Runs an event loop.
     */
    private void runEventLoop() {
        long t = System.currentTimeMillis();
        if (t - lastTime < T_THRESH) {
            return;
        }
        lastTime = t;
        Display disp = Display.getDefault();
        if (disp == null) {
            return;
        }

        ExceptionHandler handler = ExceptionHandler.getInstance();

        for (;;) {
            try {
                if (!disp.readAndDispatch()) {
