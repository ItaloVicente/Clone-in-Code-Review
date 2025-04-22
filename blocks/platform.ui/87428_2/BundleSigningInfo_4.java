					} catch (IOException e1) {
						return new Status(IStatus.ERROR,
								WorkbenchPlugin.PI_WORKBENCH, e1.getMessage(), e1);
					} catch (GeneralSecurityException e2) {
						return new Status(IStatus.ERROR,
								WorkbenchPlugin.PI_WORKBENCH, e2.getMessage(), e2);
					}
					return Status.OK_STATUS;
				});
