	    ISelection selection = fViewer.getSelection();
	    assertTrue(selection instanceof IStructuredSelection);
	    List expectedList = new ArrayList();
	    expectedList.add(expected);
	    IStructuredSelection structuredSelection = (IStructuredSelection)selection;
	    assertEquals("selectionEquals - " + message, expectedList, (structuredSelection).toList());
