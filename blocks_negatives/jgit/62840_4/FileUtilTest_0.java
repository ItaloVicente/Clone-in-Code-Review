		try {
			fs.createSymLink(new File(trash, "x"), "y");
		} catch (IOException e) {
			if (fs.supportsSymlinks())
				fail("FS claims to support symlinks but attempt to create symlink failed");
			return;
		}
