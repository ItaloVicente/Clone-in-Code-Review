	public void testPushWithOptions() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			System.out.println("PushOptionsTest: git2 = " + git2);

			final StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config, "test");
			remoteConfig.addURI(new URIish(
					git2.getRepository().getDirectory().toURI().toURL()));
			remoteConfig.addFetchRefSpec(
					new RefSpec("+refs/heads/*:refs/remotes/origin/*"));
			remoteConfig.update(config);
			config.save();

			final StoredConfig config2 = git2.getRepository().getConfig();
			config2.setBoolean("receive", null, "pushoptions", true);
			config2.save();

			writeTrashFile("f", "content of f");
			git.add().addFilepattern("f").call();
			RevCommit commit = git.commit().setMessage("adding f").call();

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			List<String> pushOptions = Arrays.asList("Hello", "World!");
			PushCommand pushCommand = git.push().setRemote("test")
					.setPushOptions(pushOptions);
			pushCommand.call();
			assertEquals(commit.getId(),
					git2.getRepository().resolve("refs/heads/branchtopush"));
			Map<Long, List<String>> timedPushOptions = git2.getRepository()
					.getTimedPushOptions();
			assertEquals(0, timedPushOptions.size());
		}
	}

