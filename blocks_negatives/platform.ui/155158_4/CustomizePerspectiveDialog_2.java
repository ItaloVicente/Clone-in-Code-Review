		if ((text != null && text.length() != 0) || (menuItem instanceof MHandledMenuItem)
				|| menuItem.getWidget() != null) {
			IContributionItem contributionItem;
			if (menuItem instanceof MMenu) {
				contributionItem = menuMngrRenderer.getManager((MMenu) menuItem);
			} else {
				contributionItem = menuMngrRenderer.getContribution(menuItem);
			}
			if (contributionItem == null) {
				return dynamicEntry;
			}
			if (dynamicEntry != null && contributionItem.equals(dynamicEntry.getIContributionItem())) {
				dynamicEntry.addCurrentItem((MenuItem) menuItem.getWidget());
			} else {
				ImageDescriptor iconDescriptor = null;
				String iconURI = menuItem.getIconURI();
				if (iconURI != null && iconURI.length() > 0) {
					iconDescriptor = resUtils.imageDescriptorFromURI(URI.createURI(iconURI));
				}

				if (menuItem.getWidget() instanceof MenuItem) {
					MenuItem item = (MenuItem) menuItem.getWidget();
					if (text == null) {
							return dynamicEntry;
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
					}
				}
				DisplayItem menuEntry = new DisplayItem(text, contributionItem);
