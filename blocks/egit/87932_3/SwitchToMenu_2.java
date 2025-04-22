			String currentBranch = repositories[0].getFullBranch();
			for (int i = 1; i < repositories.length; i++) {
				if (currentBranch != null && !currentBranch
						.equals(repositories[i].getFullBranch())) {
					currentBranch = null;
				}
			}
			Set<String> sortedRefs = new TreeSet<>(
