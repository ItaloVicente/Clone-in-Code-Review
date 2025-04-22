			commitTemplateFile = fileSystem.resolve(null
		}
		if (!commitTemplateFile.isAbsolute()) {
			commitTemplateFile = fileSystem.resolve(
					repository.getWorkTree().getAbsoluteFile()
					commitTemplatePath);
