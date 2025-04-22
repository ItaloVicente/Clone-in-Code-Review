		String xmiUriArg = System.getProperty(org.eclipse.e4.ui.workbench.IWorkbench.XMI_URI_ARG);
		if (!("org.eclipse.ui.workbench/LegacyIDE.e4xmi".equals(xmiUriArg))) { //$NON-NLS-1$
			contribProcessor.processModel(ModelAssembler.PURE_E4);
		} else {
			contribProcessor.processModel(ModelAssembler.LEGACY_E4STEP);
		}
