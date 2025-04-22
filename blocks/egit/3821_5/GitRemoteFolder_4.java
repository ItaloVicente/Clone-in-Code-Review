				GitRemoteResource obj;
				ObjectId id = diffEntry.getOldId().toObjectId();
				if (FileMode.TREE == diffEntry.getOldMode())
					obj = new GitRemoteFolder(repo, member, getCommitId(), id,
							memberPath);
				else
					obj = new GitRemoteFile(repo, getCommitId(), id, memberPath);
