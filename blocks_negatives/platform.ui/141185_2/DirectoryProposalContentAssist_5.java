	/**
	 * Wait until the asynchronous proposal refresh is finished. This method is
	 * intended to by used by tests.
	 *
	 * @param timeout timeout in milliseconds
	 * @throws TimeoutException     if the wait timed out
	 * @throws ExecutionException   if the future completed exceptionally
	 * @throws InterruptedException if the current thread was interrupted while
	 *                              waiting
	 */
	protected void wait(int timeout) throws InterruptedException, ExecutionException, TimeoutException {
		CompletableFuture.allOf(proposalUpdateFutures.toArray(new CompletableFuture[proposalUpdateFutures.size()]))
				.get(timeout, TimeUnit.MILLISECONDS);
	}

