							e.getLocalizedMessage(),
							SWT.SHEET);
                    return false;
                }
                IPreferencesService service = Platform.getPreferencesService();
                try {
                    IExportedPreferences prefs = service.readPreferences(fis);

                    service.applyPreferences(prefs, filters);
                } catch (CoreException e) {
                    WorkbenchPlugin.log(e.getMessage(), e);
					MessageDialog.open(MessageDialog.ERROR, getControl()
							.getShell(), "", e.getLocalizedMessage(), //$NON-NLS-1$
