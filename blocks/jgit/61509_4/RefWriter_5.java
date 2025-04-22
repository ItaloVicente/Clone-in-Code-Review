			ObjectId objectId = r.getObjectId();
			if (objectId == null) {
				throw new TransportException(MessageFormat
						.format(JGitText.get().cannotRead
			}
			objectId.copyTo(tmp
