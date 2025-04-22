				if (ActionFactory.NEW.getId().equals(contributionItem.getId())) {
					initializeNewWizardsMenu(menuEntry);
					wizards = menuEntry;
				} else if (SHORTCUT_CONTRIBUTION_ITEM_ID_OPEN_PERSPECTIVE.equals(contributionItem.getId())) {
					initializePerspectivesMenu(menuEntry);
					perspectives = menuEntry;
				} else if (SHORTCUT_CONTRIBUTION_ITEM_ID_SHOW_VIEW.equals(contributionItem.getId())) {
					initializeViewsMenu(menuEntry);
					views = menuEntry;
				} else {
						createMenuEntries((MMenu) menuItem, menuEntry);
					}
				}
