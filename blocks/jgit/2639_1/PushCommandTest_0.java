	@Test
	public void testTrackingUpdate() throws Exception {
		Repository db2 = createBareRepository();

		String remote = "origin";
		String branch = "refs/heads/master";
		String trackingBranch = "refs/remotes/" + remote + "/master";

		RefUpdate trackingBranchRefUpdate = db.updateRef(trackingBranch);
		trackingBranchRefUpdate.setNewObjectId(db.resolve(branch));
		trackingBranchRefUpdate.update();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);

		config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REMOTE
		config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGE
		config.save();

		Git git = new Git(db);
		RevCommit commit = git.commit().setMessage("Commit to push").call();

		RefSpec spec = new RefSpec(branch + ":" + branch);
		git.push().setRemote(remote).setRefSpecs(spec).call();

		assertEquals(commit.getId()
		assertEquals(commit.getId()
	}

