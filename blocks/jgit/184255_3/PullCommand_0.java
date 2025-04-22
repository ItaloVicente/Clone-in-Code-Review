			r = fetchRes.getAdvertisedRef(remoteBranchName);
			if (r == null) {
				r = fetchRes
						.getAdvertisedRef(Constants.R_HEADS + remoteBranchName);
			}
			if (r == null) {
				try {
					r = repo.getRefDatabase().findRef(Constants.FETCH_HEAD);
				} catch (IOException e) {
					throw new RefNotFoundException(
							MessageFormat.format(JGitText.get().refNotResolved
									Constants.FETCH_HEAD)
							e);
