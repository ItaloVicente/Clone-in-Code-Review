			ObjectId refCommitId = sha1(uri
			RevCommit commit = repo.parseCommit(refCommitId);
			TreeWalk tw = TreeWalk.forPath(repo
			tw.setRecursive(true);

			return new RemoteFile(
					readContent(repo
					tw.getFileMode(0));
		}

		protected byte[] readContent(Repository repo
				throws IOException
