			for (final String branch : remoteBranches) {
				if (branch.equals(Constants.HEAD)) {
					continue;
				}
				try {
					git._branchCreate().setName(branch)
							.setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
							.setStartPoint(remote.getKey() + "/" + branch).setForce(true).call();
				} catch (Throwable t) {
					throw new RuntimeException("Error creating branch [" + branch + "].");
				}
			}
			return null;
		} catch (final InvalidRemoteException e) {
			throw e;
		} catch (final RuntimeException re) {
			throw re;
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}
