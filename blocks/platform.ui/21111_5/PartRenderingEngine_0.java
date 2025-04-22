				Object o = appContext.get(E4Workbench.NO_SAVED_MODEL_FOUND);
				if (o != null) {
					if (((Boolean) o).booleanValue()) {
						String xmiUriArg = System
								.getProperty(org.eclipse.e4.ui.workbench.IWorkbench.XMI_URI_ARG);
						if ("org.eclipse.ui.workbench/LegacyIDE.e4xmi".equals(xmiUriArg)) { //$NON-NLS-1$
							ModelAssembler contribProcessor = ContextInjectionFactory
									.make(ModelAssembler.class, appContext);
							contribProcessor
									.processModel(ModelAssembler.LEGACY_E3STEP);
						}
					}
				}

