    ViewResponse response=null;
    try {
      response = future.get();
    } catch (ExecutionException ex) {
      Logger.getLogger(ViewTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InterruptedException ex) {
      Logger.getLogger(ViewTest.class.getName()).log(Level.SEVERE, null, ex);
    }
    assert future.getStatus().isSuccess() : future.getStatus();
