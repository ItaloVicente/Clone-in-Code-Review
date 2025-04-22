						gitDir.getPath());
			}

			if (local.hasCommonDirectory()) {
				File gitCommonDir = local.getCommonDirectory();
				if (gitCommonDir != null) {
					pb.environment().put(Constants.GIT_COMMON_DIR_KEY
							gitCommonDir.getPath());
				}
			}
