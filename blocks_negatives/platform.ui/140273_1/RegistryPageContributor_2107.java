				throw new CoreException(
						new Status(
								IStatus.ERROR,
								WorkbenchPlugin.PI_WORKBENCH,
								IStatus.ERROR,
								"Property page must implement IWorkbenchPropertyPageMulti: " + getPageName(), //$NON-NLS-1$
								null));
