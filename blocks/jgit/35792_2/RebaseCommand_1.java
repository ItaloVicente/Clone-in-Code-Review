			if (!isFixupOrSquash(step)) {
				final String oldSHA = commitToPick.getName();
				final String newSHA = repo.resolve(Constants.HEAD).getName();
				if (!oldSHA.equals(newSHA) || isFixupOrSquash(nextStep))
					recordRewrite(oldSHA
			}
