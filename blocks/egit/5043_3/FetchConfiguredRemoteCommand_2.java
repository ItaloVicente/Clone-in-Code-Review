		if (node instanceof RemoteNode)
			try {
				RemoteNode remote = (RemoteNode) node;
				return new RemoteConfig(node.getRepository().getConfig(),
						remote.getObject());
			} catch (URISyntaxException e) {
				throw new ExecutionException(e.getMessage());
			}

