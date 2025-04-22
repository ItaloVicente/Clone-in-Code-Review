		try {
			return !getSelection(null).isEmpty();
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
			return false;
		}
