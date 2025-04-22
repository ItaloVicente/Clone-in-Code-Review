		try {
			return getRepository(false, null) != null;
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
			return false;
		}
