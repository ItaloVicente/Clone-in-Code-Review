		private ProcessBuilder createProcess(List<String> args) {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(args);
			File directory = local != null ? local.getDirectory() : null;
			if (directory != null) {
				pb.environment().put(Constants.GIT_DIR_KEY
						directory.getPath());
			}
			return pb;
		}

