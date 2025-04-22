		if (transferConfig.isAllowTipSha1InWant()) {
			setRequestPolicy(transferConfig.isAllowReachableSha1InWant()
				? RequestPolicy.TIP : RequestPolicy.REACHABLE_COMMIT_TIP);
		} else {
			setRequestPolicy(transferConfig.isAllowReachableSha1InWant()
				? RequestPolicy.ADVERTISED : RequestPolicy.REACHABLE_COMMIT);
		}
