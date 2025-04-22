					psItem.addListener(SWT.Dispose, new Listener() {
						@Override
						public void handleEvent(org.eclipse.swt.widgets.Event event) {
							Image currentImage = psItem.getImage();
							if (currentImage != null)
								currentImage.dispose();
						}
