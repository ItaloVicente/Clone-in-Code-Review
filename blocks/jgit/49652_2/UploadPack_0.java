		if (transferConfig.isAllowTipSha1InWant()) {
			setRequestPolicy(transferConfig.isAllowReachableSha1InWant()
				? RequestPolicy.REACHABLE_COMMIT_TIP : RequestPolicy.TIP);
		} else {
			setRequestPolicy(transferConfig.isAllowReachableSha1InWant()
				? RequestPolicy.REACHABLE_COMMIT : RequestPolicy.ADVERTISED);
		}
