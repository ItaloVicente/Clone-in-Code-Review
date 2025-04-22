					if (menuItem.getWidget() instanceof MenuItem) {
						MenuItem item = (MenuItem) menuItem.getWidget();
						if (text == null) {
								continue;
							}
							text = item.getText();
						}
						if (iconDescriptor == null) {
							Image image = item.getImage();
							if (image != null) {
								iconDescriptor = ImageDescriptor.createFromImage(image);
							}
						}
					} else if (menuItem instanceof MHandledMenuItem) {
						MHandledMenuItem hmi = (MHandledMenuItem) menuItem;
						final String i18nLabel = hmi.getLocalizedLabel();
						if (i18nLabel != null) {
							text = i18nLabel;
						} else if (hmi.getWbCommand() != null) {
							try {
								text = hmi.getWbCommand().getName();
							} catch (NotDefinedException e) {
							}
