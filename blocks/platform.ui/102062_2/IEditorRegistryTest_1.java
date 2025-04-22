
	public void testRemoveContentType_bug520239() throws CoreException {
		ContentTypeManager contentTypeManager = (ContentTypeManager) Platform.getContentTypeManager();
		IContentType contentType = contentTypeManager.addContentType("bug520239", "bug520239", null);
		contentType.addFileSpec("bug520239", IContentType.FILE_EXTENSION_SPEC);
		assertArrayEquals("No editor should be bound by default", new IEditorDescriptor[0],
				fReg.getEditors("blah.bug520239"));

		IEditorDescriptor anEditor = ((EditorRegistry) fReg).getSortedEditorsFromPlugins()[0];
		((EditorRegistry) fReg).addUserAssociation(contentType, anEditor);
		assertArrayEquals("Missing editor association", new IEditorDescriptor[] { anEditor },
				fReg.getEditors("blah.bug520239"));

		contentTypeManager.removeContentType(contentType.getId());
		assertArrayEquals("No editor should be bound after contenttype removal", new IEditorDescriptor[0],
				fReg.getEditors("blah.bug520239"));
	}
