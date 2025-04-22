			IPropertyChangeListener badListener = event -> {
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
				if (m != null) {
					IMemento[] msets = m.getChildren(IWorkbenchConstants.TAG_WORKING_SET);
					if (msets.length != sets.length) {
						error.set("Working set lost due the bad listener! " + "restored: " + Arrays.toString(sets)
								+ ", expected: " + Arrays.toString(msets));
