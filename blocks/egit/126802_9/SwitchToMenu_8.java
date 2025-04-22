
		boolean allRepositoriesCheckedOut = true;
		Map<Repository, String> repoToFullNameMap = new HashMap<>();
		for (Repository repository : repositories) {
			Map<String, Ref> refs = repository.getRefDatabase()
					.getRefs(Constants.R_HEADS);
			String fullName = refs.get(shortName).getName();
			repoToFullNameMap.put(repository, fullName);
			allRepositoriesCheckedOut &= fullName
					.equals(repository.getFullBranch());
		}
		if (allRepositoriesCheckedOut)
