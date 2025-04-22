    return true;
  }

  private void initializeReactorThread() {
    final IOEventDispatch ioEventDispatch = new DefaultHttpClientIODispatch(
      new HttpAsyncRequestExecutor(),
      ConnectionConfig.DEFAULT
    );

    reactorThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          ioReactor.execute(ioEventDispatch);
        } catch (InterruptedIOException ex) {
          getLogger().error("I/O reactor Interrupted", ex);
        } catch (IOException e) {
          getLogger().error("I/O error: " + e.getMessage(), e);
        }
        getLogger().debug("I/O reactor terminated");
      }
    }, "Couchbase View Thread");
    reactorThread.start();

    running = true;
