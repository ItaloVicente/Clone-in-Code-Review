			createDropDownMenuMgr();
			Menu menu = new Menu(parent);
			IContributionItem[] items = dropDownMenuMgr.getItems();
			for (IContributionItem item : items) {
				IContributionItem newItem = item;
				if (item instanceof ActionContributionItem) {
					newItem = new ActionContributionItem(((ActionContributionItem) item).getAction());
				}
				newItem.fill(menu, -1);
			}
			return menu;
		}

		@Override
