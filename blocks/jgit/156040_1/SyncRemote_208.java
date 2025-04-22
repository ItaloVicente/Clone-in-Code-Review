			for (final String localBranch : localBranches) {
				if (localBranch.equals(Constants.HEAD)) {
					continue;
				}
				if (remoteBranches.contains(localBranch)) {
					try {
						git._branchCreate().setName(localBranch)
								.setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
								.setStartPoint(remote.getKey() + "/" + localBranch).setForce(true).call();
					} catch (Throwable t) {
						throw new RuntimeException("Error creating branch [" + localBranch + "].");
					}
				}
			}
