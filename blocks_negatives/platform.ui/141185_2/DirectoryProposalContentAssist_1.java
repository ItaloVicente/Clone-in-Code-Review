	/**
	 * The {@link ContentProposalAdapter} closes the proposal pop-up when the
	 * proposals are empty. This remembers the popup state and reopen the popup
	 * after the proposals have been updated by the asynchronous job.
	 */
	private boolean popupActivated = false;
	private List<CompletableFuture<Void>> proposalUpdateFutures = Collections.synchronizedList(new ArrayList<>());
