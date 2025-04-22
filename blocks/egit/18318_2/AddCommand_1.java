		if (dialog.open() == Window.OK) {
			for (String dir : wizard.getDirectories()) {
				File repositoryDir = new File(dir);
				addRepository(repositoryDir);
			}
		}
