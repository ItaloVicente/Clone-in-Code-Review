	}

	public void testFileAddition() throws CoreException, PartInitException {
		createTestFolder(); // create the project and folder before the Navigator is shown
		showNav();
		createTestFile(); // create the file after the Navigator is shown

		ISelectionProvider selProv = navigator.getSite().getSelectionProvider();
		StructuredSelection sel = new StructuredSelection(testFile);
		selProv.setSelection(sel);
		assertEquals(sel.size(), ((IStructuredSelection)selProv.getSelection()).size());
		assertEquals(sel.getFirstElement(), ((IStructuredSelection)selProv.getSelection()).getFirstElement());
	}
