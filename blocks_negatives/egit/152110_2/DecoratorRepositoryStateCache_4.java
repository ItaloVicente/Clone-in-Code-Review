					String branchName = getFullBranchName(repository);
					if (branchName == null) {
					}
					BranchTrackingStatus status = null;
					try {
						status = BranchTrackingStatus.of(repository,
								branchName);
					} catch (IOException e) {
					}
					if (status == null) {
					}
					if (status.getAheadCount() == 0
							&& status.getBehindCount() == 0) {
