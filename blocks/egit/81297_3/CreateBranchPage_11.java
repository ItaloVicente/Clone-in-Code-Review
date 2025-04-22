		if (baseRef != null) {
			this.upstreamConfig = CreateLocalBranchOperation
					.getDefaultUpstreamConfig(repo, baseRef.getName());
		} else {
			this.upstreamConfig = null;
		}
