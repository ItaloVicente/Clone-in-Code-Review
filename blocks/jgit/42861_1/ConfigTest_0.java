
		mockSystemReader.setProperty(Constants.GIT_AUTHOR_NAME_KEY
				"git author name");
		mockSystemReader.setProperty(Constants.GIT_AUTHOR_EMAIL_KEY
				"author@email");
		localConfig.setString("user"
		localConfig.setString("user"
		authorName = localConfig.get(UserConfig.KEY).getAuthorName();
		authorEmail = localConfig.get(UserConfig.KEY).getAuthorEmail();
		assertEquals("git author name"
		assertEquals("author@email"
		assertFalse(localConfig.get(UserConfig.KEY).isAuthorNameImplicit());
		assertFalse(localConfig.get(UserConfig.KEY).isAuthorEmailImplicit());
