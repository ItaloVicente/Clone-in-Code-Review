			final String fullBranch;
			try {
				fullBranch = repo.getFullBranch();
			} catch (IOException e) {
				throw new InvocationTargetException(e);
			}
