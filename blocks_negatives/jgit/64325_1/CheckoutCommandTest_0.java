		Git git2 = new Git(db2);

		final StoredConfig config = db2.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config, "origin");
		URIish uri = new URIish(db.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		RefSpec spec = new RefSpec("+refs/heads/*:refs/remotes/origin/*");
		git2.fetch().setRemote("origin").setRefSpecs(spec).call();
		return db2;
