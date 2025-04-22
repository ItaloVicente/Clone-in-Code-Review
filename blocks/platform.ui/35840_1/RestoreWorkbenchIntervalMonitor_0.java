    @Override
	public void eventLoopIdle(Display d) {
        if (createRestorableWorkbench) {
			workbenchConfigurer.getWorkbench().restart();
		} else {
			super.eventLoopIdle(d);
		}
