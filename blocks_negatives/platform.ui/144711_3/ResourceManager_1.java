		} catch (DeviceResourceException e) {
			Policy.getLog().log(
					new Status(IStatus.WARNING, "org.eclipse.jface", 0, //$NON-NLS-1$
							"The image could not be loaded: " + descriptor, //$NON-NLS-1$
							e));
			return getDefaultImage();
		} catch (SWTException e) {
