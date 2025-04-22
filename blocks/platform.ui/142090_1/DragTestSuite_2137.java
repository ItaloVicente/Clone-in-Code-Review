			DragTest newTest = new DragTest(dragSource, dropTarget, getLog());
			addTest(newTest);
		}
	}

	private void addAllCombinationsDetached(TestDragSource dragSource,
			TestDropLocation[] dropTargets) {

		if (isDetachingSupported) {
			for (TestDropLocation dropTarget : dropTargets) {
				DragTest newTest = new DetachedWindowDragTest(dragSource, dropTarget, getLog());
				addTest(newTest);
			}
		}
	}
