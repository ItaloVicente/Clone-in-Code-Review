			}
		}
		assertEquals("Export failed to Export all directories",
				folderCount, directories.size());

		for (int i = 0; i < directories.size(); i++) {
			File directory = directories.get(i);
			assertTrue("Export failed to export directory " + directory.getName(), directory.exists());
			verifyFolder(directory);
		}
