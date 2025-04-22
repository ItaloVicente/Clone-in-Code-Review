			String projectFilePath = projectLocation.append(
					IProjectDescription.DESCRIPTION_FILE_NAME).toOSString();
			File projectFile = new File(projectFilePath);
			if (projectFile.exists()) {
				final File file = p.getLocation().toFile();
				if (file.getAbsolutePath().startsWith(
						parentFile.getAbsolutePath()))
					result.add(p);
