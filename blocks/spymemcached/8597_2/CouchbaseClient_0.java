	public ViewResponseWithDocs query(View view, Query query) {
		try {
			return asyncQuery(view, query).get();
		}catch (InterruptedException e) {
			throw new RuntimeException("Interrupted while accessing the view", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Failed to access the view", e);
		}
	}

	public ViewResponseNoDocs queryAndExcludeDocs(View view, Query query) {
		try {
			return asyncQueryAndExcludeDocs(view, query).get();
		}catch (InterruptedException e) {
			throw new RuntimeException("Interrupted while accessing the view", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Failed to access the view", e);
		}
	}

	public ViewResponseReduced queryAndReduce(View view, Query query) {
		try {
			return asyncQueryAndReduce(view, query).get();
		}catch (InterruptedException e) {
			throw new RuntimeException("Interrupted while accessing the view", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Failed to access the view", e);
		}
	}

