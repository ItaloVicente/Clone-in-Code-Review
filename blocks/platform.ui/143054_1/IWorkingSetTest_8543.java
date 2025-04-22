	public void testGetElements() throws Throwable {
		assertEquals(fWorkspace.getRoot(), fWorkingSet.getElements()[0]);
	}

	public void testGetId() throws Throwable {
		assertEquals(null, fWorkingSet.getId());
		fWorkingSet.setId("bogusId");
		assertEquals("bogusId", fWorkingSet.getId());
		fWorkingSet.setId(null);
		assertEquals(null, fWorkingSet.getId());
	}

	public void testGetName() throws Throwable {
		assertEquals(WORKING_SET_NAME_1, fWorkingSet.getName());
	}

	public void testSetElements() throws Throwable {
		boolean exceptionThrown = false;

		try {
			fWorkingSet.setElements(null);
		} catch (RuntimeException exception) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);

		IProject p1 = FileUtil.createProject("TP1");
		IFile f1 = FileUtil.createFile("f1.txt", p1);
		IAdaptable[] elements = new IAdaptable[] { f1, p1 };
		fWorkingSet.setElements(elements);
		assertTrue(ArrayUtil.equals(elements, fWorkingSet.getElements()));

		fWorkingSet.setElements(new IAdaptable[] { f1 });
		assertEquals(f1, fWorkingSet.getElements()[0]);

		fWorkingSet.setElements(new IAdaptable[] {});
		assertEquals(0, fWorkingSet.getElements().length);
	}

	public void testSetId() throws Throwable {
		assertEquals(null, fWorkingSet.getId());
		fWorkingSet.setId("bogusId");
		assertEquals("bogusId", fWorkingSet.getId());
		fWorkingSet.setId(null);
		assertEquals(null, fWorkingSet.getId());
	}

	public void testSetName() throws Throwable {
		boolean exceptionThrown = false;

		try {
			fWorkingSet.setName(null);
		} catch (RuntimeException exception) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);

		fWorkingSet.setName(WORKING_SET_NAME_2);
		assertEquals(WORKING_SET_NAME_2, fWorkingSet.getName());

		exceptionThrown = false;
