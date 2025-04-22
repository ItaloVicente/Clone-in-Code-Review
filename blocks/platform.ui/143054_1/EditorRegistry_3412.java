		int nDot = filename.lastIndexOf('.');
		if (nDot >= 0) {
			String strName = filename.substring(nDot);
			return Program.findProgram(strName) != null;
		}
		return false;
	}

	@Override
	public ImageDescriptor getSystemExternalEditorImageDescriptor(String filename) {
		Program externalProgram = null;
		int extensionIndex = filename.lastIndexOf('.');
		if (extensionIndex >= 0) {
			externalProgram = Program.findProgram(filename.substring(extensionIndex));
		}
		if (externalProgram == null) {
			return null;
		}

		return new ExternalProgramImageDescriptor(externalProgram);
	}

	private void removeEditorFromMapping(HashMap<String, FileEditorMapping> map, IEditorDescriptor desc) {
		Iterator<FileEditorMapping> iter = map.values().iterator();
