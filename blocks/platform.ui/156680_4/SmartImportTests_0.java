	@Test
	public void testBug559600() throws Exception {
		AtomicInteger errors = new AtomicInteger();
		ILogListener errorListener = new ILogListener() {
			@Override
			public void logging(IStatus status, String plugin) {
				if (status.getSeverity() == IStatus.ERROR) {
					errors.incrementAndGet();
				}
			}
		};

		CharArrayWriter existingDialogSettings = new CharArrayWriter();
		SmartImportWizard wizard = new SmartImportWizard();
		try {
			Platform.addLogListener(errorListener);
			wizard.getDialogSettings().save(existingDialogSettings);
			wizard.setInitialImportSource(File.listRoots()[0]);
			wizard.getDialogSettings().put("SmartImportRootWizardPage.STORE_HIDE_ALREADY_OPEN", true);
			wizard.getDialogSettings().put("SmartImportRootWizardPage.STORE_NESTED_PROJECTS", false);
			this.dialog = new WizardDialog(getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
			dialog.setBlockOnOpen(false);
			dialog.open();
			SmartImportRootWizardPage page = (SmartImportRootWizardPage) dialog.getCurrentPage();
			ProgressMonitorPart wizardProgressMonitor = page.getWizardProgressMonitor();
			processEventsUntil(() -> !wizardProgressMonitor.isVisible(), 10000);
			assertEquals("Label provider (or something else) produced error", 0, errors.get());
			assertFalse("Searching projects should be done within 10 seconds", wizardProgressMonitor.isVisible());
		} finally {
			dialog.close();
			Platform.removeLogListener(errorListener);
			wizard.getDialogSettings().load(new CharArrayReader(existingDialogSettings.toCharArray()));
		}
	}

