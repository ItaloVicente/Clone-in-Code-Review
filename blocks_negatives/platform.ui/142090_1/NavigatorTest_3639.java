    }

    /**
     * Tests that the Navigator updates properly when a file is added to the workbench.
     */
    public void testFileAddition() throws CoreException, PartInitException {
        showNav();

        ISelectionProvider selProv = navigator.getSite().getSelectionProvider();
        StructuredSelection sel = new StructuredSelection(testFile);
        selProv.setSelection(sel);
        assertEquals(sel.size(), ((IStructuredSelection)selProv.getSelection()).size());
        assertEquals(sel.getFirstElement(), ((IStructuredSelection)selProv.getSelection()).getFirstElement());
    }
