		if (!fetchAll) {
			RefSpec tags = new RefSpec();
			tags = tags.setForceUpdate(true);
			tags = tags.setSourceDestination(Constants.R_TAGS + '*',
					Constants.R_TAGS + '*');
			for (String selectedRef : branchesToClone) {
				if (heads.matchSource(selectedRef)) {
					specs.add(heads.expandFromSource(selectedRef));
				} else if (tags.matchSource(selectedRef)) {
					specs.add(tags.expandFromSource(selectedRef));
