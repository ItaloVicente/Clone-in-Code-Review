					DisplayItem menuEntry = new DisplayItem(text, contributionItem);

					if (iconDescriptor != null) {
						menuEntry.setImageDescriptor(iconDescriptor);
					}
					menuEntry.setActionSet(idToActionSet.get(getActionSetID(menuItem)));
					parent.addChild(menuEntry);

					if (ActionFactory.NEW.getId().equals(contributionItem.getId())) {
						initializeNewWizardsMenu(menuEntry);
						wizards = menuEntry;
					} else if (SHORTCUT_CONTRIBUTION_ITEM_ID_OPEN_PERSPECTIVE
							.equals(contributionItem.getId())) {
						initializePerspectivesMenu(menuEntry);
						perspectives = menuEntry;
					} else if (SHORTCUT_CONTRIBUTION_ITEM_ID_SHOW_VIEW.equals(contributionItem
							.getId())) {
						initializeViewsMenu(menuEntry);
						views = menuEntry;
					} else {
							createMenuEntries((MMenu) menuItem, menuEntry, trackDynamics);
