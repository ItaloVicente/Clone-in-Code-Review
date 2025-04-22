 * This interface is now returned from all asyncGetBulk
 * methods. Unlike {@link #get(long, TimeUnit)},
 * {@link #getSome(long, TimeUnit)} does not throw
 * CheckedOperationTimeoutException, thus allowing retrieval
 * of partial results after timeout occurs. This behavior is
 * especially useful in case of large multi gets.
