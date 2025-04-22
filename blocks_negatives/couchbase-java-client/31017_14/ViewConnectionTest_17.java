  /**
   * Tests the correctness of the initialization and shutdown phase.
   *
   * @pre Create a list of array of addresses and get a connection
   * factory instance from them. Create view connection using the
   * parameters as above.
   * @post Assert false if the view connection nodes are empty
   * Shutdown the client and then again check for assertion.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws InterruptedException the interrupted exception
   */
