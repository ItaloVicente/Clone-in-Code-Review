			try {
				IPath inputPath = pathEditorInput.getPath();
				return getEditorForPath(inputPath);
			} catch (Exception e) {
				return Optional.empty();
			}
