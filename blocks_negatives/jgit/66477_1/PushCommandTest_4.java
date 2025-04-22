		RefUpdate trackingBranchRefUpdate = db.updateRef(trackingBranch);
		trackingBranchRefUpdate.setNewObjectId(commit1.getId());
		trackingBranchRefUpdate.update();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config, remote);
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.addFetchRefSpec(new RefSpec("+refs/heads/*:refs/remotes/"
				+ remote + "/*"));
		remoteConfig.update(config);
		config.save();
