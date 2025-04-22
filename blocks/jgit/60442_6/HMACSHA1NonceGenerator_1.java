		} else {
			File directory = repo.getDirectory();
			if (directory != null) {
				path = directory.getPath();
			} else {
				throw new IllegalStateException();
			}
		}
