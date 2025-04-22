	protected void pushTo(File localClone) throws Exception {
		pushTo(null
	}

	protected void pushTo(CredentialsProvider provider
			throws Exception {
		RevCommit commit;
		File newFile = null;
		try (Git git = Git.open(localClone)) {
			Repository local = git.getRepository();
			newFile = File.createTempFile("new"
					local.getWorkTree());
			write(newFile
			File existingFile = new File(local.getWorkTree()
			write(existingFile
			git.add().addFilepattern("file.txt")
					.addFilepattern(newFile.getName())
					.call();
			commit = git.commit().setMessage("Local commit").call();
			PushCommand push = git.push().setPushAll();
			if (provider != null) {
				push.setCredentialsProvider(provider);
			}
			Iterable<PushResult> results = push.call();
			for (PushResult result : results) {
				for (RemoteRefUpdate u : result.getRemoteUpdates()) {
					assertEquals(
							"Could not update " + u.getRemoteName() + ' '
									+ u.getMessage()
							RemoteRefUpdate.Status.OK
				}
			}
		}
		assertEquals("Unexpected remote commit"
		assertEquals("Unexpected remote commit"
				db.resolve(Constants.HEAD));
		File remoteFile = new File(db.getWorkTree()
		assertFalse("File should not exist on remote"
		try (Git git = new Git(db)) {
			git.reset().setMode(ResetType.HARD).setRef(Constants.HEAD).call();
		}
		assertTrue("File does not exist on remote"
		checkFile(remoteFile
	}

	protected class TestCredentialsProvider extends CredentialsProvider {
