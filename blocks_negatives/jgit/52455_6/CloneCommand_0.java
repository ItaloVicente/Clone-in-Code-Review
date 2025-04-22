		if (cloneAllBranches)
			specs.add(wcrs);
		else if (branchesToClone != null
				&& branchesToClone.size() > 0) {
			for (String selectedRef : branchesToClone)
				if (wcrs.matchSource(selectedRef))
