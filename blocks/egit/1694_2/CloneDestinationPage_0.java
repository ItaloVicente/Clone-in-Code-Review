			String destinationDir = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.DEFAULT_REPO_DIR);
			File parentDir = new File(destinationDir);
			if (!parentDir.exists() || !parentDir.isDirectory()) {
				parentDir = ResourcesPlugin.getWorkspace().getRoot()
						.getRawLocation().toFile();
			}
			directoryText.setText(new File(parentDir, n).getAbsolutePath());
