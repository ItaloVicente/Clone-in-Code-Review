			String bugtrackerUrl = getConfiguredUrl(r);
			if (bugtrackerUrl == null) {
				AbstractTaskReference ref = runResolverExtension(getRepoRoot(r), message, commitName);
				if (ref != null)
					return ref;
			}

			if (bugtrackerUrl == null)
				bugtrackerUrl = getOriginUrl(r);

