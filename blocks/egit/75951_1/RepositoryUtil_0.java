		} else {
			IScopeContext[] contexts = new IScopeContext[] {
					InstanceScope.INSTANCE, ConfigurationScope.INSTANCE,
					DefaultScope.INSTANCE };
			for (IScopeContext context : contexts) {
				dir = context.getNode(Activator.getPluginId()).get(key, null);
				if (dir != null) {
					break;
				}
			}
			if (dir == null) {
				dir = getDefaultDefaultRepositoryDir();
			}
