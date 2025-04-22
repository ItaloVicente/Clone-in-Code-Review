		if (node instanceof RepositoryNode) {
			RemoteConfig config = getRemoteConfigCached(node);
			if (config == null) {
				return false;
			}
			List<RefSpec> refSpecs = config.getPushRefSpecs();
			if (!refSpecs.isEmpty()) {
				return true;
			}
			PushDefault pushDefault = SelectionRepositoryStateCache.INSTANCE
					.getConfig(node.getRepository()).get(PushConfig::new)
					.getPushDefault();
			return !PushDefault.NOTHING.equals(pushDefault);

		}
