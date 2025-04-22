		assertEquals(activePage.getActivePart(), saveableView);
		PropertySheetPage page = (PropertySheetPage) propertySheet.getCurrentPage();
		assertEquals(saveableView, page.getAdapter(ISaveablePart.class));
		assertEquals(saveableView, propertySheet.getAdapter(ISaveablePart.class));
		assertFalse(propertySheet.isDirtyStateSupported());
	}

	@Override
	protected void doTearDown() throws Exception {
		Platform.getAdapterManager().unregisterAdapters(adapterFactory);
		activePage.resetPerspective();
		super.doTearDown();
	}

	@Test
	public void testIsDirtyStateIndicationSupported() throws Exception {
		assertTrue(isDirtyStateSupported(saveableView));

		saveableView.setAdapter(ISecondarySaveableSource.class, dirtyAllowed);
		assertTrue(isDirtyStateSupported(propertySheet));

		saveableView.setAdapter(ISecondarySaveableSource.class, dirtyDisallowed);
		assertFalse(isDirtyStateSupported(propertySheet));

		saveableView.setAdapter(ISecondarySaveableSource.class, null);
		assertFalse(isDirtyStateSupported(propertySheet));

		adapterFactory.setAdapter(ISecondarySaveableSource.class, dirtyAllowed);
		Platform.getAdapterManager().registerAdapters(adapterFactory, ISecondarySaveableSource.class);
		assertTrue(isDirtyStateSupported(propertySheet));
	}
