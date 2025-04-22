		try {
			if (ref != null && ref.getName().equals(repository.getFullBranch()))
				ref = null;
		} catch (IOException ignored) {
		}

