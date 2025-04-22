		String fetchUrl = config.getString(REMOTE, remote.getObject(), URL);
		config.unset(REMOTE, remote.getObject(), FETCH);
		config.unset(REMOTE, remote.getObject(), URL);
		if (fetchUrl != null) {
			boolean hasPush = config.getStringList(REMOTE, remote.getObject(),
					PUSH).length > 0;
			if (hasPush) {
				String[] pushurls = config.getStringList(REMOTE, remote
						.getObject(), PUSHURL);
				if (pushurls.length == 0)
					config.setString(REMOTE, remote.getObject(), PUSHURL,
							fetchUrl);
			}
		}
