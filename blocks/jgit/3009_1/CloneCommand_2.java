	private List<RefSpec> calculateRefSpecs(final String dst) {
		RefSpec wcrs = new RefSpec();
		wcrs = wcrs.setForceUpdate(true);
		wcrs = wcrs.setSourceDestination(Constants.R_HEADS + "*"
		List<RefSpec> specs = new ArrayList<RefSpec>();
		if (cloneAllBranches)
			specs.add(wcrs);
		else if (branchesToClone != null
				&& branchesToClone.size() > 0) {
			for (final String selectedRef : branchesToClone)
				if (wcrs.matchSource(selectedRef))
					specs.add(wcrs.expandFromSource(selectedRef));
		}
		return specs;
	}

