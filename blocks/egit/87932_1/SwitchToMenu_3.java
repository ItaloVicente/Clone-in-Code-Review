			Set<String> localBranches = new TreeSet<>(
					CommonUtils.STRING_ASCENDING_COMPARATOR);
			for (Repository repository : repositories) {
				Map<String, Ref> local = repository.getRefDatabase()
						.getRefs(Constants.R_HEADS);
				TreeMap<String, Ref> refs = new TreeMap<>(
						CommonUtils.STRING_ASCENDING_COMPARATOR);
