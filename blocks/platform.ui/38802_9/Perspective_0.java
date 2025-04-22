			List<String> alwaysOn = ModeledPageLayout.getIds(layout, ModeledPageLayout.ACTION_SET_TAG);

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

			alwaysOn.removeAll(alwaysOff);

			for (IActionSetDescriptor descriptor : createInitialActionSets(alwaysOn)) {
