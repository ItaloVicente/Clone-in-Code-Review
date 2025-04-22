			} else if (listMode == ListMode.REMOTE) {
				refs.addAll(repo.getRefDatabase().getRefsByPrefix(R_REMOTES));
			} else {
				refs.addAll(repo.getRefDatabase().getRefsByPrefix(R_HEADS,
						R_REMOTES));
			}
