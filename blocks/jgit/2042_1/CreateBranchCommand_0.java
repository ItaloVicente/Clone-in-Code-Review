			RefUpdate updateRef;

			if (name.startsWith(Constants.R_HEADS)) {
				updateRef = repo.updateRef(name);
			} else {
				updateRef = repo.updateRef(Constants.R_HEADS + name);
			}

