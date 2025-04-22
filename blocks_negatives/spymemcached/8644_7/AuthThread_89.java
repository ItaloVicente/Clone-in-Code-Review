	private Operation buildOperation(OperationStatus st, OperationCallback cb) {
		if(st == null) {
			return opFact.saslAuth(authDescriptor.mechs,
					node.getSocketAddress().toString(), null,
					authDescriptor.cbh, cb);
		} else {
			return opFact.saslStep(authDescriptor.mechs,
					KeyUtil.getKeyBytes(st.getMessage()),
					node.getSocketAddress().toString(), null,
					authDescriptor.cbh, cb);
		}
