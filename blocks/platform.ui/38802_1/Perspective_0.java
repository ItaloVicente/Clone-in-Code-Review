
			String hiddenIDs = page.getHiddenItems();
			List<String> alwaysOff = new ArrayList<String>();

			String[] hiddenIds = hiddenIDs.split(","); //$NON-NLS-1$
			for (String id : hiddenIds) {
				if (!id.startsWith(ModeledPageLayout.HIDDEN_ACTIONSET_PREFIX)) {
					continue;
				}
				id = id.substring(ModeledPageLayout.HIDDEN_ACTIONSET_PREFIX.length());
				if (!alwaysOff.contains(id)) {
					alwaysOff.add(id);
				}
			}

			ids.removeAll(alwaysOff);

			List<IActionSetDescriptor> temp = new ArrayList<IActionSetDescriptor>();
