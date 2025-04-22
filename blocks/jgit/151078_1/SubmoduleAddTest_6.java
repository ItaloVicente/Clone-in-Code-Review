			try (SubmoduleWalk generator = SubmoduleWalk.forIndex(db)) {
				assertTrue(generator.next());
				assertEquals(path
				assertEquals(commit
				assertEquals(uri
				assertEquals(path
				String fullUri = db.getDirectory().getAbsolutePath();
				if (File.separatorChar == '\\') {
					fullUri = fullUri.replace('\\'
				}
				assertEquals(fullUri
				try (Repository subModRepo = generator.getRepository()) {
					assertNotNull(subModRepo);
					assertEquals(fullUri
							subModRepo.getConfig().getString(
									ConfigConstants.CONFIG_REMOTE_SECTION
									Constants.DEFAULT_REMOTE_NAME
									ConfigConstants.CONFIG_KEY_URL));
				}
