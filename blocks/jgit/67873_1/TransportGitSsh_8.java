						gitDir.getPath());
			}

			if (local.isGitCommonDir()) {
				File gitCommonDir = local.getGitCommonDir();
				if (gitCommonDir != null) {
					pb.environment().put(Constants.GIT_COMMON_DIR_KEY
							gitCommonDir.getPath());
				}
			}
