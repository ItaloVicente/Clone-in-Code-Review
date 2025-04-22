			IPath workingDir = gitDirPath.removeLastSegments(1);
			if (workingDir.isRoot())
				return false;

			File userHome = FS.DETECTED.userHome();
			if (userHome != null) {
				Path userHomePath = new Path(userHome.getAbsolutePath());
				if (workingDir.isPrefixOf(userHomePath))
					return false;
			}

			final File repositoryDir = gitDirPath.toFile();
			projects.put(project, repositoryDir);

			try {
				Activator.getDefault().getRepositoryUtil()
						.addConfiguredRepository(repositoryDir);
