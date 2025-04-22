
	public void testAddContentTypeBinding_bug502837() {
		IEditorDescriptor[] editors = fReg.getEditors("blah.bug502837");
		assertEquals(1, editors.length);
		assertEquals(MockEditorPart.ID1, editors[0].getId());
	}
