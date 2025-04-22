		List<IEditorDescriptor> externalEditors = new ArrayList<>();
		for (Program program : Program.getPrograms()) {
			/*
			 * if (fileName.charAt(0) == '%') { fileName = name + ".exe"; }
			 */

			EditorDescriptor editor = new EditorDescriptor();
			editor.setOpenMode(EditorDescriptor.OPEN_EXTERNAL);
			editor.setProgram(program);

			ImageDescriptor desc = new ExternalProgramImageDescriptor(program);
			editor.setImageDescriptor(desc);
			externalEditors.add(editor);
		}

		Object[] tempArray = sortEditors(externalEditors);
		IEditorDescriptor[] array = new IEditorDescriptor[externalEditors.size()];
		for (int i = 0; i < tempArray.length; i++) {
			array[i] = (IEditorDescriptor) tempArray[i];
		}
		return array;
