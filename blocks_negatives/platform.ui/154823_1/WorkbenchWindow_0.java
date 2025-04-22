			} else if (item instanceof CommandContributionItem) {
				CommandContributionItem cci = (CommandContributionItem) item;
				MMenuItem menuItem = MenuHelper.createItem(application, cci);
				manager.remove(item);
				if (menuItem != null) {
					menu.getChildren().add(menuItem);
				}
