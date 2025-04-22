			Ref ref;
			if (name.startsWith(Constants.R_HEADS))
				ref = repo.getRef(name);
			else
				ref = null;
			RefUpdate refUpdate = repo.updateRef(Constants.HEAD
