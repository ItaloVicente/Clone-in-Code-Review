			assertArrayEquals(new IWorkingSet[] { wSetA, wSetC }, wSetB.getComponents());

			IMemento workingSets = saveAndRemoveWorkingSets(wSetA, wSetB, wSetC);
			processEvents();
			waitForJobs(500, 3000);

			assertNull(manager.getWorkingSet(nameA));
			assertNull(manager.getWorkingSet(nameB));
			assertNull(manager.getWorkingSet(nameC));

			restoreWorkingSetManager(workingSets);
			processEvents();
			waitForJobs(500, 3000);

			IWorkingSet restoredA = manager.getWorkingSet(nameA);
			assertNotNull("Unable to save/restore correctly", restoredA);

			IAggregateWorkingSet restoredB = (IAggregateWorkingSet) manager.getWorkingSet(nameB);
