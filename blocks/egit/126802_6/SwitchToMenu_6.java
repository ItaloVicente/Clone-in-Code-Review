			if (itemCount >= MAX_NUM_MENU_ENTRIES) {
				return itemCount;
			}

			List<Map<String, Ref>> localBranchMapping = new ArrayList<>();
			for (Repository repository : repositories) {
				Map<String, Ref> localBranches = repository.getRefDatabase()
						.getRefs(Constants.R_HEADS);
				localBranchMapping.add(localBranches);
