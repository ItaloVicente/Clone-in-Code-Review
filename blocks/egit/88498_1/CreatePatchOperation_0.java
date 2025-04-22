			try {
				if (commit != null) {
					RevCommit[] parents = commit.getParents();
					if (parents.length > 1) {
						throw new IllegalStateException(
								CoreText.CreatePatchOperation_cannotCreatePatchForMergeCommit);
					}
