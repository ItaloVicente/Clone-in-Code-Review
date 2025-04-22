
	@Test
	public void testPushWithLease() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		try (Git git1 = new Git(db)) {
			RevCommit commit = git1.commit().setMessage("initial commit").call();
			Ref branchRef = git1.branchCreate().setName("initial").call();

			RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
			git1.push().setRemote("test").setRefSpecs(spec)
					.call();

			assertEquals(commit.getId()
					db2.resolve(commit.getId().getName() + "^{commit}"));

			RevCommit commit2 = git1.commit().setMessage("second commit").call();
			Iterable<PushResult> results =
					git1.push().setRemote("test").setRefSpecs(spec)
							.setRefLeaseSpecs(new RefLeaseSpec("refs/heads/x"
							.call();
			for (PushResult result : results) {
				RemoteRefUpdate update = result.getRemoteUpdate("refs/heads/x");
				assertEquals(update.getStatus()
			}

			RevCommit commit3 = git1.commit().setMessage("third commit").call();

			results =
					git1.push().setRemote("test").setRefSpecs(spec)
							.setRefLeaseSpecs(new RefLeaseSpec("refs/heads/x"
							.call();
			for (PushResult result : results) {
				RemoteRefUpdate update = result.getRemoteUpdate("refs/heads/x");
				assertEquals(update.getStatus()
			}
		}
	}
