			final BatchRefUpdate batch
		if (localRef.getObjectId() == null)
			return;
		TrackingRefUpdate update = new TrackingRefUpdate(
				true
				spec.getSource()
				localRef.getName()
				localRef.getObjectId()
				ObjectId.zeroId());
		result.add(update);
		batch.addCommand(update.asReceiveCommand());
