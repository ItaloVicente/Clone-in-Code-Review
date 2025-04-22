	public void testFindExternalEditor() {
		IEditorDescriptor[] sortedEditorsFromOS = ((EditorRegistry) fReg).getSortedEditorsFromOS();
		assertThat("The OS should have at least one external editor", sortedEditorsFromOS.length,
				Matchers.greaterThan(1));
		for (IEditorDescriptor ied : sortedEditorsFromOS) {
			EditorDescriptor ed = (EditorDescriptor) ied;
			EditorDescriptor found = (EditorDescriptor) fReg.findEditor(ed.getId());
			assertNotNull("Found editor must not be null", found);
			assertEquals("External editor should be found using find(id)", ed.getProgram(), found.getProgram());
		}
	}

