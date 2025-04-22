			String message = executableExtension == null ?
					"The startup extension does not provide a valid class attribute." : //$NON-NLS-1$
					MessageFormat.format("Startup class {0} must implement org.eclipse.ui.IStartup", //$NON-NLS-1$
							executableExtension.getClass().getName());
			IStatus status =
					new Status(IStatus.ERROR, extension.getNamespaceIdentifier(), 0, message, null);
			WorkbenchPlugin.log(status);
