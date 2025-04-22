
		if (layoutJob == null) {
			layoutJob = new LayoutJob();
			layoutJob.barsToLayout.add(trimBar);
			comp.getDisplay().asyncExec(layoutJob);
		} else if (!layoutJob.barsToLayout.contains(trimBar)) {
			layoutJob.barsToLayout.add(trimBar);
