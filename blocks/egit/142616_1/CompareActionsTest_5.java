			try (ByteArrayInputStream bis = new ByteArrayInputStream(
					"Modified".getBytes("UTF-8"))) {
				ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFolder(FOLDER).getFile(FILE2)
				.setContents(bis, false, false, null);
			}
