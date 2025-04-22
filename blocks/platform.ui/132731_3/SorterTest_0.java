		IFile modelFile = _project.getFile(TestContentProvider.MODEL_FILE_PATH);
		try (InputStream modelInput = modelFile.getContents()) {
			Properties model = new Properties();
			model.load(modelInput);
			model.setProperty(TestContentProvider.MODEL_ROOT,
					model.getProperty(TestContentProvider.MODEL_ROOT) + ",AddedParent");
			model.putAll(props);
			ByteArrayOutputStream newContent = new ByteArrayOutputStream();
			model.store(newContent, null);
			modelFile.setContents(new ByteArrayInputStream(newContent.toByteArray()), 0, null);
		}

