			assertEquals(expectedUnix, actual);

			oldOSName = System.getProperty("os.name");
			try {
				System.setProperty("os.name", "Mac OS X");

				actual = FileUtils.relativize(base, other);
				assertEquals(expectedWindows, actual);
			} finally {
				if (oldOSName != null)
					System.setProperty("os.name", oldOSName);
			}
