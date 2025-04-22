					if (Boolean.parseBoolean(System.getProperty("e4.impExpPerspectiveEnabled"))) { //$NON-NLS-1$
						try {
							migrationProcessor = ContextInjectionFactory.make(
								WorkbenchMigrationProcessor.class, context);
						} catch (@SuppressWarnings("restriction") InjectionException e) {
							WorkbenchPlugin.log(e);
						}
