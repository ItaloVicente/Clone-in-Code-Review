			StringBuilder defaultBuilder = new StringBuilder();
			defaultBuilder.append(System.getProperty("user.dir")); //$NON-NLS-1$
			defaultBuilder.append(File.separator);
			String launcherName = System.getProperty("eclipse.launcher.name"); //$NON-NLS-1$
			if (launcherName != null) {
				defaultBuilder.append(launcherName);
				defaultBuilder.append('-');
			}
			defaultBuilder.append("workspace"); //$NON-NLS-1$
			setInitialDefault(defaultBuilder.toString());
