     * Given a position defined by k and an array of size n, this fills in the array with
     * the kth smallest element through to the (k+n)th smallest element. For example, 
     * getRange(myArray, 10, false) would fill in myArray starting with the 10th smallest item
     * in the collection. The result can be computed in sorted or unsorted order. Computing the
     * result in unsorted order is more efficient.
     * <p>
     * Temporarily set to package visibility until the implementation of FastProgressReporter
     * is finished.
     * </p>
     * 
     * @param result array to be filled in
     * @param rangeStart index of the smallest element to appear in the result
     * @param sorted true iff the result array should be sorted
     * @param mon progress monitor used to cancel the operation
     * @throws InterruptedException if the progress monitor was cancelled in another thread
     */
