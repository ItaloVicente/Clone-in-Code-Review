				if (!hasChanges) {
					if (keepIndexCommit != null) {
						new ResetCommand(repo)
								.setRef(keepIndexCommit.getName())
								.setMode(ResetType.SOFT).call();
					}
