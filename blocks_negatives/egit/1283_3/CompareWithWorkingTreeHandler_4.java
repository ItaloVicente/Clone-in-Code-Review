		try {
			int size = getSelection(null).size();
			return IFile.class.isAssignableFrom(getInput(null).getClass())
					&& size == 1;
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
