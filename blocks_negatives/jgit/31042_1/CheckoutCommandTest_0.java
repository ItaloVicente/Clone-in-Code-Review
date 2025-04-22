		final StoredConfig config = db2.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config, "origin");
		URIish uri = new URIish(db.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();
