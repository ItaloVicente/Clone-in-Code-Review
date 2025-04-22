		Assert.fail("DND needs some updating");
	}

	public static String getName(IEditorPart editor) {
		IWorkbenchPage page = editor.getSite().getPage();
		IWorkbenchPartReference ref = page.getReference(editor);
		return ref.getPartName();
	}
