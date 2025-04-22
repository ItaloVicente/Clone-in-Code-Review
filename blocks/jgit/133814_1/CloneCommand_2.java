	private void calculateTagRefSpecs(List<RefSpec> refSpecs) {
		RefSpec tags = new RefSpec();
		tags = tags.setForceUpdate(true);
		tags = tags.setSourceDestination(Constants.R_TAGS + '*'
				Constants.R_TAGS + '*');
		for (String selectedRef : branchesToClone) {
			if (tags.matchSource(selectedRef)) {
				refSpecs.add(tags.expandFromSource(selectedRef));
			}
		}
	}

