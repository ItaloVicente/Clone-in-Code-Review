
	@Test
	public void testCancelWizardCancelsJob() {
		SmartImportWizard wizard = new SmartImportWizard();
		wizard.setInitialImportSource(File.listRoots()[0]);
		this.dialog = new WizardDialog(getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		dialog.setBlockOnOpen(false);
		dialog.open();
		SmartImportRootWizardPage page = (SmartImportRootWizardPage) dialog.getCurrentPage();
		ProgressMonitorPart wizardProgressMonitor = page.getWizardProgressMonitor();
		assertNotNull("Wizard should have a progress monitor", wizardProgressMonitor);
		ToolItem stopButton = getStopButton(wizardProgressMonitor);
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return stopButton.isEnabled();
			}
		}, 10000);
		assertTrue("Wizard should show progress monitor", wizardProgressMonitor.isVisible());
		assertTrue("Stop button should be enabled", stopButton.isEnabled());
		Event clickButtonEvent = new Event();
		clickButtonEvent.widget = stopButton;
		clickButtonEvent.item = stopButton;
		clickButtonEvent.type = SWT.Selection;
		clickButtonEvent.doit = true;
		clickButtonEvent.stateMask = SWT.BUTTON1;
		stopButton.notifyListeners(SWT.Selection, clickButtonEvent);
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return !wizardProgressMonitor.isVisible();
			}
		}, 10000);
		assertFalse("Progress monitor should be hidden within 10 seconds", wizardProgressMonitor.isVisible());
	}

	private static ToolItem getStopButton(ProgressMonitorPart part) {
		for (Control control : part.getChildren()) {
			if (control instanceof ToolBar) {
				for (ToolItem item : ((ToolBar) control).getItems()) {
					if (item.getToolTipText().equals(JFaceResources.getString("ProgressMonitorPart.cancelToolTip"))) { //$NON-NLS-1$ ))
						return item;
					}
				}
			}
		}
		return null;
	}
