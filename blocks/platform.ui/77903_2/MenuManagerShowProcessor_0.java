			} else if (currentMenuElement instanceof MMenu) {
				MMenu childMenu = (MMenu) currentMenuElement;
				MenuManager childManager = renderer.getManager(childMenu);
				renderer.removeDynamicMenuContributions(childManager, childMenu);
				processDynamicElements(childMenu, childManager);
				childManager.update(true);
