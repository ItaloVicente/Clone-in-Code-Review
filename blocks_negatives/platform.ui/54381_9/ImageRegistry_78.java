    private Runnable disposeRunnable = new Runnable() {
        @Override
		public void run() {
            dispose();
        }
    };
