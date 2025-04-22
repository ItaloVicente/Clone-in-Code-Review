
		if (node instanceof RemoteNode || node instanceof PushNode) {
			RemoteNode remoteNode;
			if (node instanceof PushNode)
				remoteNode = (RemoteNode) node.getParent();
			else
				remoteNode = (RemoteNode) node;

