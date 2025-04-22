			for (Repository repository : repositories) {
				if (!repository.isBare()) {
					GitSynchronizeData data = new GitSynchronizeData(
							repository, Constants.HEAD, Constants.HEAD, true);
					set.add(data);
				}
