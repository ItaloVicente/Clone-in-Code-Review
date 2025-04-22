			StringBuilder defaultBuilder = new StringBuilder();
			defaultBuilder.append(System.getProperty("user.dir")); //$NON-NLS-1$
			defaultBuilder.append(File.separator);
			String launcherPath = System.getProperty("eclipse.launcher"); //$NON-NLS-1$
			if (launcherPath != null) {
				defaultBuilder.append(new File(launcherPath).getName());
				defaultBuilder.append('-');
			}
			defaultBuilder.append("workspace"); //$NON-NLS-1$
			setInitialDefault(defaultBuilder.toString());
