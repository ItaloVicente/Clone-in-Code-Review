		try {
			Set<ObjectId> immediateRefs = new HashSet<>();
			for (ReceiveCommand cmd : commands) {
				if (cmd.getType() == ReceiveCommand.Type.UPDATE || cmd
						.getType() == ReceiveCommand.Type.UPDATE_NONFASTFORWARD) {
					if (advertisedHaves.contains(cmd.getOldId())) {
						immediateRefs.add(cmd.getOldId());
					}
				}
			}
			checkConnectivity(baseObjects
					checking);
			return;
		} catch (MissingObjectException e) {
		}
		checkConnectivity(baseObjects
				checking);

	}

	private void checkConnectivity(ObjectIdSubclassMap<ObjectId> baseObjects
			ObjectIdSubclassMap<ObjectId> providedObjects
			ProgressMonitor checking)
			throws MissingObjectException
