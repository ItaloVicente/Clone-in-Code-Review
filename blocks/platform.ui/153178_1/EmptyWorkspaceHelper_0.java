	}

	public void setEmptinessDetector(EmptinessDetector emptinessDetector) {
		this.emptinessDetector = emptinessDetector;
		registerListeners(); // Must happen after the detector is set to avoid an NPE.
		switchTopControl(); // Must happen after setNonEmptyControl() is called.
	}

	public void updateEmptiness() {
		PlatformUI.getWorkbench().getDisplay()
				.asyncExec(() -> PlatformUI.getWorkbench().getDisplay().timerExec(200, switchTopControlRunnable));
