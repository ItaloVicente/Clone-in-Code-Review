			ProcessBuilder pb = new ProcessBuilder();
			pb.command(args);

			File directory = local.getDirectory();
			if (directory != null)
				pb.environment().put(Constants.GIT_DIR_KEY,
						directory.getPath());

