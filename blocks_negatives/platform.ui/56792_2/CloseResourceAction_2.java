		if (input instanceof FileEditorInput) {
			FileEditorInput fi = (FileEditorInput) input;
			IFile file = fi.getFile();
			if (file != null) {
				return file;
			}
		}
