		verify(local, remoteResource);
	}

	private ObjectId stageAndCommit(String fileName, byte[] content)
			throws Exception {
		ObjectId objectId = stage(fileName, content);
		commit();

		return objectId;
	}

	private ObjectId stage(String fileName, byte[] content) throws Exception {
		GitIndex index = repo.getIndex();
		File workdir = project.getProject().getFullPath().toFile();
		File file = new File(workdir, fileName);
		Entry entry = index.add(workdir, file, content);
		index.write();

		return entry.getObjectId();
	}

	private RevCommit commit() throws Exception {
		Git git = new Git(repo);
		CommitCommand commit = git.commit();
		commit.setMessage("Initial  commit");
		commit.setAuthor("EGit", "egi@eclipse.org");
		return commit.call();
