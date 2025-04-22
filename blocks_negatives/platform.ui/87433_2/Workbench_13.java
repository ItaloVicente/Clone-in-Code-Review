					WorkbenchMigrationProcessor migrationProcessor = null;
					try {
						migrationProcessor = ContextInjectionFactory.make(WorkbenchMigrationProcessor.class, context);
					} catch (@SuppressWarnings("restriction") InjectionException e) {
						WorkbenchPlugin.log(e);
					}
