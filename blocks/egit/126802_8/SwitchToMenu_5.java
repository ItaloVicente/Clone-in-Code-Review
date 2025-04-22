			List<Map<String, Ref>> activeBranches = new ArrayList<>();

			for (Repository repository : repositories) {
				Map<String, Ref> branchRefMapping = getMostActiveBranches(
						repository, MAX_NUM_MENU_ENTRIES);
				activeBranches.add(branchRefMapping);
