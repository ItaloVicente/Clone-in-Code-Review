					return UnitOfWork.get(repository, () -> {
						String branchName = getFullBranchName(repository);
						if (branchName == null) {
							return ""; //$NON-NLS-1$
						}
						BranchTrackingStatus status = null;
						try {
							status = BranchTrackingStatus.of(repository,
									branchName);
						} catch (IOException e) {
						}
						if (status == null) {
							return ""; //$NON-NLS-1$
						}
						if (status.getAheadCount() == 0
								&& status.getBehindCount() == 0) {
							return ""; //$NON-NLS-1$
						}
						return GitLabels.formatBranchTrackingStatus(status);

					});
