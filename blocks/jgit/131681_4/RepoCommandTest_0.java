			ObjectId refCommitId = sha1(uri
			RevCommit commit = repo.parseCommit(refCommitId);
			TreeWalk tw = TreeWalk.forPath(repo

			return new RemoteFile(
					tw.getObjectReader().open(tw.getObjectId(0))
							.getCachedBytes()
					tw.getFileMode(0));
		}

		@Override
		public byte[] readFile(String uri
				throws GitAPIException
			return readFileWithMode(uri
