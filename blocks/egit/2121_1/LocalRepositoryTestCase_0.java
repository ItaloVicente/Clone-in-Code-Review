	protected static Collection<Ref> getRemoteRefs(URIish uri) throws Exception {
		final Repository db = new FileRepository(new File("/tmp")); //$NON-NLS-1$
		int timeout = 20;
		ListRemoteOperation listRemoteOp = new ListRemoteOperation(db, uri,
				timeout);
		listRemoteOp.run(null);
		return listRemoteOp.getRemoteRefs();
	}
