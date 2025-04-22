        assertTrue("'Property' view should be visible", activePage.isPartVisible(propView));
        assertFalse("'Project Explorer' view should be hidden", activePage
                .isPartVisible(projectExplorer));
        assertTrue("'Selection provider' view should be visible", activePage
                .isPartVisible(selectionProviderView));
    }

    /**
     * Tests that the Properties view provides the source part for getAdapter(ISaveablePart.class)
     * if it's saveable.
     * See  Bug 125386 [PropertiesView] Properties view should delegate Save back to source part
     */
    public void testSaveableRetargeting() throws Throwable {
        PropertySheetPerspectiveFactory.applyPerspective(activePage);
    	IWorkbenchPart propView = createTestParts(activePage);
    	assertNull(propView.getAdapter(ISaveablePart.class));
    	IViewPart saveableView = activePage.showView(SaveableMockViewPart.ID);
    	activePage.activate(propView);
    	assertEquals(saveableView, propView.getAdapter(ISaveablePart.class));
    }
