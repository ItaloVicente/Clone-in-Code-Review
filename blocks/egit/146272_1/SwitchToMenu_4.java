			try {
				for (Repository repository : repositories) {
					Map<String, Ref> branchRefMapping = getMostActiveBranches(
							repository, MAX_NUM_MENU_ENTRIES);
					activeBranches.add(branchRefMapping);
				}
			} catch (IOException e) {
				Activator.logError(e.getLocalizedMessage(), e);
				activeBranches.clear();
