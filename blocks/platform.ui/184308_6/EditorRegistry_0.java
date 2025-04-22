
	private static class LazyOSEditorsHolder {
		static final Map<String, IEditorDescriptor> INSTANCE = computeIDtoOSEditors();

		private static Map<String, IEditorDescriptor> computeIDtoOSEditors() {
			Map<String, IEditorDescriptor> map = new HashMap<>();
			IEditorDescriptor[] sortedEditorsFromOS = getStaticSortedEditorsFromOS();
			for (IEditorDescriptor desc : sortedEditorsFromOS) {
				map.put(desc.getId(), desc); // ignore duplicates
			}
			return map;
		}
	}

	private static class LazySortedEditorsFromOSHolder {
		static final IEditorDescriptor[] INSTANCE = computeSortedEditorsFromOS();

		private static IEditorDescriptor[] computeSortedEditorsFromOS() {
			List<IEditorDescriptor> externalEditors = new ArrayList<>();

			final Program[] programs[] = new Program[1][];
			Display.getDefault().syncExec(() -> {
				programs[0] = Program.getPrograms();
			});
			for (Program program : programs[0]) {

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
		}
	}
