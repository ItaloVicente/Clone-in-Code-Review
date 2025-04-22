	public void testWorkingSetSaveNeverRestoresAggregate() throws Throwable {
		IWorkingSetManager manager = fWorkbench.getWorkingSetManager();
		String nameA = "A";
		String nameB = "B";
		String nameC = "C";

		IWorkingSet wSetA = manager.createWorkingSet(nameA, new IAdaptable[] {});
		manager.addWorkingSet(wSetA);

		IAggregateWorkingSet wSetB = (IAggregateWorkingSet) manager.createAggregateWorkingSet(nameB, nameB,
				new IWorkingSet[] {});
		manager.addWorkingSet(wSetB);

		IAggregateWorkingSet wSetC = (IAggregateWorkingSet) manager.createAggregateWorkingSet(nameC, nameC,
				new IWorkingSet[0]);
		manager.addWorkingSet(wSetC);

		try {
			assertEquals("Failed to add workingset" + nameA, wSetA, manager.getWorkingSet(nameA));

			assertEquals("Failed to add workingset" + nameC, wSetC, manager.getWorkingSet(nameC));

			assertEquals("Failed to add workingset" + nameB, wSetB, manager.getWorkingSet(nameB));

			assertEquals(0, wSetB.getComponents().length);

			invokeMethod(AggregateWorkingSet.class, "setComponents", wSetB,
					new Object[] { new IWorkingSet[] { wSetA, wSetC } },
					new Class[] { new IWorkingSet[] {}.getClass() });

			assertArrayEquals(new IWorkingSet[] { wSetA, wSetC }, wSetB.getComponents());

			IMemento workingSets = saveAndRemoveWorkingSets(wSetA, wSetB, wSetC);
			processEvents();
			waitForJobs(500, 3000);

			assertNull(manager.getWorkingSet(nameA));
			assertNull(manager.getWorkingSet(nameB));
			assertNull(manager.getWorkingSet(nameC));

			final AtomicReference<String> error = new AtomicReference<>();

			IPropertyChangeListener badListener = new IPropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getProperty() != IWorkingSetManager.CHANGE_WORKING_SET_ADD) {
						return;
					}
					Object ws = event.getNewValue();
					if (!(ws instanceof AggregateWorkingSet)) {
						return;
					}
					AggregateWorkingSet aws = (AggregateWorkingSet) ws;
					IMemento m = readField(AbstractWorkingSet.class, "workingSetMemento", IMemento.class, aws);
					IWorkingSet[] sets = aws.getComponents();
					IMemento[] msets = m.getChildren(IWorkbenchConstants.TAG_WORKING_SET);
					if (msets.length != sets.length) {
						error.set("Working set lost due the bad listener! " + "restored: " + Arrays.toString(sets)
								+ ", expected: " + Arrays.toString(msets));
					}
				}
			};
			try {
				manager.addPropertyChangeListener(badListener);

				restoreWorkingSetManager(workingSets);
				processEvents();

				IWorkingSet restoredA = manager.getWorkingSet(nameA);
				assertNotNull("Unable to save/restore correctly", restoredA);

				IAggregateWorkingSet restoredB = (IAggregateWorkingSet) manager.getWorkingSet(nameB);
				assertNotNull("Unable to save/restore correctly", restoredB);

				IAggregateWorkingSet restoredC = (IAggregateWorkingSet) manager.getWorkingSet(nameC);
				assertNotNull("Unable to save/restore correctly", restoredC);

				IWorkingSet[] componenents1 = wSetB.getComponents();
				IWorkingSet[] componenents2 = restoredB.getComponents();
				assertEquals(2, componenents1.length);
				assertEquals(1, componenents2.length);
				assertNotNull(error.get());
			} finally {
				manager.removePropertyChangeListener(badListener);
			}

		} finally {
			IWorkingSet set = manager.getWorkingSet(nameA);
			if (set != null) {
				manager.removeWorkingSet(set);
			}
			set = manager.getWorkingSet(nameB);
			if (set != null) {
				manager.removeWorkingSet(set);
			}
			set = manager.getWorkingSet(nameC);
			if (set != null) {
				manager.removeWorkingSet(set);
			}
		}
	}

	private IMemento saveAndRemoveWorkingSets(IWorkingSet... sets) {
