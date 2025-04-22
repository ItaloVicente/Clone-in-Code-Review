				ObjectId baseObjectId = baseBlob.getObjectId();
				ObjectId remoteObjectId = remoteBlob.getObjectId();
				if (getComparator().compare(local, base)) {
					description = calculateDescBasedOnGitCommits(baseBlob,
							remoteBlob);
				} else if (baseObjectId.equals(remoteObjectId)) {
