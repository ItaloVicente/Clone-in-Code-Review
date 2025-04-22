        for (TestDropLocation dropTarget : dropTargets) {
        	if (dropTarget == null) {
				continue;
			}

            DragTest newTest = new DragTest(dragSource, dropTarget, getLog());
