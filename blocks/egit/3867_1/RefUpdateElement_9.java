	private final URIish uri;

	private final ObjectReader reader;

	private final Repository repo;

	private Object[] children;

	private final boolean tag;

	RefUpdateElement(final PushOperationResult result, RemoteRefUpdate update,
			URIish uri, ObjectReader reader, Repository repo) {
