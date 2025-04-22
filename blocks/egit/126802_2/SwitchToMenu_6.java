			List<Map<String, Ref>> localBranchMapping = new ArrayList<>();
				for (Repository repository : repositories) {
					Map<String, Ref> localBranches = repository.getRefDatabase()
							.getRefs(Constants.R_HEADS);
					localBranchMapping.add(localBranches);
				}

