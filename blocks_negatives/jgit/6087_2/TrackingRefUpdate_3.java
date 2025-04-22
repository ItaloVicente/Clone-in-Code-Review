	private final RefUpdate update;

	TrackingRefUpdate(final Repository db, final RefSpec spec,
			final AnyObjectId nv, final String msg) throws IOException {
		this(db, spec.getDestination(), spec.getSource(), spec.isForceUpdate(),
				nv, msg);
	}
