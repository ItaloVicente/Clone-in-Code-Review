
		if (repository != null) {
			try {
				fillValues(repository);
			} catch (IOException e) {
				if (GitTraceLocation.UI.isActive())
					GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
			}
		}

		return composite;
