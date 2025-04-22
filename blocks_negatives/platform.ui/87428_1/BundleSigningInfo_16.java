				} catch (IOException e) {
					return new Status(IStatus.ERROR,
							WorkbenchPlugin.PI_WORKBENCH, e.getMessage(), e);
				} catch (GeneralSecurityException e) {
					return new Status(IStatus.ERROR,
							WorkbenchPlugin.PI_WORKBENCH, e.getMessage(), e);
				}
				return Status.OK_STATUS;
			}
		});
