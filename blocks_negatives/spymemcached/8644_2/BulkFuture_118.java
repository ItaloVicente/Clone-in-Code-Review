	/**
	 * @return true if timeout was reached, false otherwise
	 */
	public boolean isTimeout();

    /**
     * Wait for the operation to complete and return results
     *
     * If operation could not complete within specified
     * timeout, partial result is returned. Otherwise, the
     * behavior is identical to {@link #get(long, TimeUnit)}
     *
     *
     * @param timeout
     * @param unit
     * @return a partial get bulk result
     * @throws InterruptedException
     * @throws ExecutionException
     */
	public V getSome(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException;

	/**
	 * Gets the status of the operation upon completion.
	 *
	 * @return the operation status.
	 */
	public OperationStatus getStatus();
