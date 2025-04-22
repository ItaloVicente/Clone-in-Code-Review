	/**
	 *
	 * Authenticate a new connection.  This is typically used by a
	 * MemcachedNode in order to authenticate a connection right after it
	 * has been established.
	 *
	 * If an old, but not yet completed authentication exists this will
	 * stop it in order to create a new authentication attempt.
	 *
	 * @param conn
	 * @param opFact
	 * @param authDescriptor
	 * @param node
	 */
	public synchronized void authConnection(MemcachedConnection conn,
		OperationFactory opFact, AuthDescriptor authDescriptor, MemcachedNode node) {
			interruptOldAuth(node);
			AuthThread newSASLAuthenticator = new AuthThread(conn, opFact,
				authDescriptor, node);
			nodeMap.put(node, newSASLAuthenticator);
	}
