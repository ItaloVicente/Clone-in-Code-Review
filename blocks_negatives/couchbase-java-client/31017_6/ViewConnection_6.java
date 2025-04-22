   * To make sure that the operations are distributed throughout the cluster,
   * the ViewNode is changed every time a new operation is added. Since the
   * getNextNode() method increments the ViewNode IDs and calculates the
   * modulo, the nodes are selected in a round-robin fashion.
   *
   * @param op the operation to run.
