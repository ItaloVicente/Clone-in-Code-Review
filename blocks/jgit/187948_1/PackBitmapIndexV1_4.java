		PackReverseIndex computedReverseIndex;
		if (parallelLoadReverseIndex && reverseIndexFuture != null) {
			try {
				computedReverseIndex = reverseIndexFuture.get();
			} catch (InterruptedException | ExecutionException e) {
				computedReverseIndex = reverseIndexSupplier.get();
			}
		} else {
			computedReverseIndex = reverseIndexSupplier.get();
		}
		this.reverseIndex = computedReverseIndex;
