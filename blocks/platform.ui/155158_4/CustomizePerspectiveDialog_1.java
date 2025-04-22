			if (iconDescriptor != null) {
				menuEntry.setImageDescriptor(iconDescriptor);
			} else if (parent.getParent() == null) {
				menuEntry.setImageDescriptor(menuImageDescriptor);
			} else {
				menuEntry.setImageDescriptor(submenuImageDescriptor);
			}

			menuEntry.setActionSet(idToActionSet.get(getActionSetID(menuItem)));

			String managerId = manager != null ? manager.getId() : null;
			if (ActionFactory.NEW.getId().equals(managerId)) {
				initializeNewWizardsMenu(menuEntry);
				wizards = menuEntry;
			} else if (SHORTCUT_CONTRIBUTION_ITEM_ID_OPEN_PERSPECTIVE.equals(managerId)) {
				initializePerspectivesMenu(menuEntry);
				perspectives = menuEntry;
			} else if (SHORTCUT_CONTRIBUTION_ITEM_ID_SHOW_VIEW.equals(managerId)) {
				initializeViewsMenu(menuEntry);
				views = menuEntry;
			} else {
				createMenuEntries((MMenu) menuItem, menuEntry);
			}
