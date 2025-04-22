			} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
				Image currentImage = ti.getImage();
				String uri = (String) newValue;
				URL url = null;
				try {
					url = new URL(uri);
					ImageDescriptor descriptor = ImageDescriptor.createFromURL(url);
					if (descriptor == null) {
						ti.setImage(null);
					} else
						ti.setImage(descriptor.createImage());
				} catch (IOException e) {
					ti.setImage(null);
					IStatus status = new Status(IStatus.WARNING, "org.eclipse.ui.workbench", //$NON-NLS-1$
							WorkbenchMessages.Icon_Not_Found, e);
					StatusManager.getManager().handle(status);
				} finally {
					if (currentImage != null)
						currentImage.dispose();
				}
