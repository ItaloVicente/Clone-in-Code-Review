			assertEquals(
					fullUri,
					subModRepo
							.getConfig()
							.getString(ConfigConstants.CONFIG_REMOTE_SECTION,
									Constants.DEFAULT_REMOTE_NAME,
									ConfigConstants.CONFIG_KEY_URL));
			subModRepo.close();
