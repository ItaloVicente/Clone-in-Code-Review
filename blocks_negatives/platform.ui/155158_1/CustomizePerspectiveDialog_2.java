			if (dynamicEntry != null && contributionItem.equals(dynamicEntry.getIContributionItem())) {
				dynamicEntry.addCurrentItem((MenuItem) menuItem.getWidget());
			} else {
				ImageDescriptor iconDescriptor = null;
				String iconURI = menuItem.getIconURI();
				if (iconURI != null && iconURI.length() > 0) {
					iconDescriptor = resUtils.imageDescriptorFromURI(URI.createURI(iconURI));
				}
