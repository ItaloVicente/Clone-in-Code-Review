					AggregateWorkingSet aws = (AggregateWorkingSet) ws;
					IMemento m = readField(AbstractWorkingSet.class, "workingSetMemento", IMemento.class, aws);
					IWorkingSet[] sets = aws.getComponents();
					if (m != null) {
						IMemento[] msets = m.getChildren(IWorkbenchConstants.TAG_WORKING_SET);
						if (msets.length != sets.length) {
							error.set("Working set lost due the bad listener! " + "restored: " + Arrays.toString(sets)
									+ ", expected: " + Arrays.toString(msets));
						}
					} else {
						if (nameB.equals(aws.getName()) && sets.length != 2) {
							error.set("Working set lost due the bad listener! " + "restored: " + Arrays.toString(sets));
						}
