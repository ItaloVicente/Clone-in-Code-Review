	}

	public void testApplicableTo_AdapterManager2() {
		fWorkingSet.setId("org.eclipse.ui.tests.api.MockWorkingSet");
		ModelElement element = new ModelElement();
		assertTrue(fWorkingSet.adaptElements(new IAdaptable[] {element}).length == 0);
	}

