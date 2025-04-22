				WorkbenchMigrationProcessor migrationProcessor = null;
				try {
					migrationProcessor = ContextInjectionFactory.make(WorkbenchMigrationProcessor.class, context);
				} catch (@SuppressWarnings("restriction") InjectionException e1) {
					WorkbenchPlugin.log(e1);
				}
