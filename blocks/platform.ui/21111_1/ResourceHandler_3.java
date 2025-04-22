
					String xmiUriArg = System
							.getProperty(org.eclipse.e4.ui.workbench.IWorkbench.XMI_URI_ARG);
					if (!("org.eclipse.ui.workbench/LegacyIDE.e4xmi".equals(xmiUriArg))) { //$NON-NLS-1$
						contribProcessor.processModel(ModelAssembler.E4ONLY);
					} else {
						contribProcessor.processModel(ModelAssembler.E3_E4STEP);
					}
