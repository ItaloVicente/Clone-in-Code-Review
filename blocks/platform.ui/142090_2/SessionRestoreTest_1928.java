		IEditorReference[] editors = pages[1].getEditorReferences();
		assertEquals(editors.length, 3);
		testSessionEditor(editors[0].getEditor(true),
				SessionCreateTest.TEST_FILE_1);
		testSessionEditor(editors[1].getEditor(true),
				SessionCreateTest.TEST_FILE_2);
		testSessionEditor(editors[2].getEditor(true),
				SessionCreateTest.TEST_FILE_3);
	}
