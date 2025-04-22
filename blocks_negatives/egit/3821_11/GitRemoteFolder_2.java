		try {
			while (tw.next()) {
				if (monitor.isCanceled())
					throw new OperationCanceledException();

				ObjectId newObjectId = tw.getObjectId(nth);

				if (!zeroId().equals(newObjectId))
					if (tw.isSubtree())
						result.add(new GitRemoteFolder(repo, getCommit(),
								newObjectId, path));
					else
						result.add(new GitRemoteFile(repo, getCommit(),
								newObjectId, path));
