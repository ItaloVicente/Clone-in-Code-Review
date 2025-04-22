		if (resources.length == 1) {
			final PreviousCommit previous = getPreviousRevision(event,
					resources[0]);
			if (previous != null) {
				try {
					CompareUtils.compare(resources, repository, Constants.HEAD,
							previous.commit.getName(), true);
				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithRefAction_errorOnSynchronize, e,
							true);
				}
			}
		}

