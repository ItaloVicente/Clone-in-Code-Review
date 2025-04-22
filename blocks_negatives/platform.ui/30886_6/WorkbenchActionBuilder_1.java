            coolbarPopupMenuManager = new MenuManager();
			coolbarPopupMenuManager.add(new ActionContributionItem(lockToolBarAction));
            coolbarPopupMenuManager.add(new ActionContributionItem(editActionSetAction));
            coolBar.setContextMenuManager(coolbarPopupMenuManager);
            IMenuService menuService = window.getService(IMenuService.class);
            menuService.populateContributionManager(coolbarPopupMenuManager, "popup:windowCoolbarContextMenu"); //$NON-NLS-1$
        }
