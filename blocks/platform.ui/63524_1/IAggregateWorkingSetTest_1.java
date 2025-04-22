			assertEquals(2, wSetB.getComponents().length);

			IMemento workingSets = saveAndRemoveAllWorkingSets();
			processEvents();
			waitForJobs(500, 3000);

			assertNull(manager.getWorkingSet(nameA));
			assertNull(manager.getWorkingSet(nameB));
			assertNull(manager.getWorkingSet(nameC));

			assertArrayEquals(new IWorkingSet[0], manager.getAllWorkingSets());

			restoreWorkingSetManager(workingSets);
			processEvents();
			waitForJobs(500, 3000);
