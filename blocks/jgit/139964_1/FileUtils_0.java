		if (delete) {
			Throwable t = null;
			Path p = f.toPath();
			try {
				Files.deleteIfExists(p);
				return;
			} catch (IOException e) {
				t = e;
			}
			if ((options & RETRY) != 0) {
