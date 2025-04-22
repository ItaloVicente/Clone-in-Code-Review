			IPath workingDir = gitDirPath.removeLastSegments(1);
			if (workingDir.isRoot()) {
				return;
			}

			File userHome = FS.DETECTED.userHome();
			if (userHome != null) {
				Path userHomePath = new Path(userHome.getAbsolutePath());
				if (workingDir.isPrefixOf(userHomePath)) {
					return;
				}
			}

