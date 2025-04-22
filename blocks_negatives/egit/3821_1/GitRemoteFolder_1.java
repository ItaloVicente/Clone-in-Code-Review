				GitRemoteResource obj = null;
				int objectType = tw.getFileMode(0).getObjectType();
				if (objectType == OBJ_BLOB)
					obj = new GitRemoteFile(repo, revCommit, objectId, tw.getPathString());
				else if (objectType == Constants.OBJ_TREE)
					obj = new GitRemoteFolder(repo, revCommit, objectId, tw.getPathString());
