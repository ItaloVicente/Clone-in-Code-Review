		File traceFile = getOptions().getFile();
		if (traceFile == null) {
			traceFileLocation.setEnabled(false);
			openButton.setEnabled(false);
		} else {
			traceFileLocation.setText(traceFile.getPath());
		}
