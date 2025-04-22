			dropDownMenuMgr.addMenuListener(new IMenuListener() {
				@Override
				public void menuAboutToShow(IMenuManager manager) {
					IContributionItem[] items = getContributionItems();
					for (int i = 0; i < items.length; i++) {
						manager.add(items[i]);
					}
					manager.add(new OpenDialogAction());
