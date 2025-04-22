			final String configName = (String) node.getObject();

			RemoteConfig rconfig;
			try {
				rconfig = new RemoteConfig(node.getRepository().getConfig(),
						configName);
			} catch (URISyntaxException e2) {
				rconfig = null;
			}

			boolean fetchExists = rconfig != null
					&& !rconfig.getURIs().isEmpty();
			boolean pushExists = rconfig != null
					&& !rconfig.getPushURIs().isEmpty();
