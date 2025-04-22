				RemoteConfig config = new RemoteConfig(remoteNode
						.getRepository().getConfig(), remoteNode.getObject());
				boolean fetchConfigured = !config.getFetchRefSpecs().isEmpty();
				boolean pushConfigured = !config.getPushRefSpecs().isEmpty();
				if (fetchConfigured || pushConfigured)
					return config;
				else
					return null;
