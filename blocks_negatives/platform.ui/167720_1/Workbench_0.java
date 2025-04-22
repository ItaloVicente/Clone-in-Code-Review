	private static WorkbenchMigrationProcessor rune3WorkbenchMigration(IEclipseContext context) {
		WorkbenchMigrationProcessor migrationProcessor = null;
		try {
			migrationProcessor = ContextInjectionFactory.make(WorkbenchMigrationProcessor.class, context);
		} catch (InjectionException e1) {
			WorkbenchPlugin.log(e1);
		}

		if (migrationProcessor != null && migrationProcessor.isLegacyWorkbenchDetected()) {
			try {
				WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.INFO, "Workbench migration started", null)); //$NON-NLS-1$
				migrationProcessor.migrate();
			} catch (Exception e2) {
				WorkbenchPlugin.log("Workbench migration failed", e2); //$NON-NLS-1$
				migrationProcessor.restoreDefaultModel();
			}
		}
		return migrationProcessor;
	}

