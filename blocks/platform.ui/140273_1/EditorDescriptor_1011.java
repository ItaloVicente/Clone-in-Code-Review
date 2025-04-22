	public static EditorDescriptor createForProgram(String filename) {
		if (filename == null) {
			throw new IllegalArgumentException();
		}
		EditorDescriptor editor = new EditorDescriptor();

		editor.setFileName(filename);
		editor.setID(filename);
		editor.setOpenMode(OPEN_EXTERNAL);

		int start = filename.lastIndexOf(File.separator);
		String name;
		if (start != -1) {
			name = filename.substring(start + 1);
		} else {
			name = filename;
		}
		int end = name.lastIndexOf('.');
		if (end != -1) {
			name = name.substring(0, end);
		}
		editor.setName(name);

		ImageDescriptor imageDescriptor = new ProgramImageDescriptor(filename, 0);
		editor.setImageDescriptor(imageDescriptor);
