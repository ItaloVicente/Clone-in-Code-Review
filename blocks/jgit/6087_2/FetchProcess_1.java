	private TrackingRefUpdate createUpdate(RefSpec spec
			throws TransportException {
		Ref ref = localRefs().get(spec.getDestination());
		ObjectId oldId = ref != null && ref.getObjectId() != null
				? ref.getObjectId()
				: ObjectId.zeroId();
		return new TrackingRefUpdate(
				spec.isForceUpdate()
				spec.getSource()
				spec.getDestination()
				oldId
				newId);
	}

	private Map<String
		if (localRefs == null) {
			try {
				localRefs = transport.local.getRefDatabase()
						.getRefs(RefDatabase.ALL);
			} catch (IOException err) {
				throw new TransportException(JGitText.get().cannotListRefs
			}
		}
		return localRefs;
