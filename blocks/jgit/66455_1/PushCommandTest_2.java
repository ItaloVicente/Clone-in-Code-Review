			final StoredConfig config = db.getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(db2.getDirectory().toURI().toURL());
			remoteConfig.addURI(uri);
			remoteConfig.update(config);
			config.save();
