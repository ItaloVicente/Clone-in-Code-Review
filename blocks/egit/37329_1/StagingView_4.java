				if (addReplaceWithOursTheirsMenu) {
					MenuManager replaceWithMenu = new MenuManager(
							UIText.StagingView_ReplaceWith);
					ReplaceWithOursTheirsMenu oursTheirsMenu = new ReplaceWithOursTheirsMenu();
					oursTheirsMenu.initialize(getSite());
					replaceWithMenu.add(oursTheirsMenu);
					menuMgr.add(replaceWithMenu);
				}
