		public IMenuCreator getMenuCreator() {
			return this;
		}

		@Override
		public Menu getMenu(Control parent) {
			if (menu != null) {
				menu.dispose();
				menu = null;
			}
			if (isEnabled()) {
				Repository current = currentRepo.get();
				File gitDir = current == null ? null : current.getDirectory();
				Collection<IAction> actions = RepositoryMenuUtil
						.getRepositoryActions(includeBare, gitDir, action);
				menu = new Menu(parent);
				for (IAction a : actions) {
					ActionContributionItem item = new ActionContributionItem(a);
					item.fill(menu, -1);
				}
			}
			return menu;
		}

		@Override
		public Menu getMenu(Menu parent) {
			return null;
