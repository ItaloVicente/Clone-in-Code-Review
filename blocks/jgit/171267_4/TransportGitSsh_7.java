			File commonDirectory = local != null ? local.getCommonDirectory()
					: null;
			if (commonDirectory != null) {
				pb.environment().put(Constants.GIT_COMMON_DIR_KEY
						commonDirectory.getPath());
			}
