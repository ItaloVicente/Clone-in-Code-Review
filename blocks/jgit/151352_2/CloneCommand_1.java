		if (type == FETCH_TYPE.MIRROR) {
			specs.add(new RefSpec().setForceUpdate(true).setSourceDestination(
					Constants.R_REFS + '*'
		} else {
			RefSpec heads = new RefSpec();
			heads = heads.setForceUpdate(true);
			final String dst = (bare ? Constants.R_HEADS
					: Constants.R_REMOTES + remoteName + '/') + '*';
			heads = heads.setSourceDestination(Constants.R_HEADS + '*'
			if (type == FETCH_TYPE.MULTIPLE_BRANCHES) {
				RefSpec tags = new RefSpec().setForceUpdate(true)
						.setSourceDestination(Constants.R_TAGS + '*'
								Constants.R_TAGS + '*');
				for (String selectedRef : branchesToClone) {
					if (heads.matchSource(selectedRef)) {
						specs.add(heads.expandFromSource(selectedRef));
					} else if (tags.matchSource(selectedRef)) {
						specs.add(tags.expandFromSource(selectedRef));
					}
