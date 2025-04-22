  private void initializeReactorThread() {
    final IOEventDispatch ioEventDispatch = new DefaultHttpClientIODispatch(
      new HttpAsyncRequestExecutor(),
      ConnectionConfig.DEFAULT
    );

    reactorThread = new Thread(new Runnable() {
      @Override
      public void run() {
