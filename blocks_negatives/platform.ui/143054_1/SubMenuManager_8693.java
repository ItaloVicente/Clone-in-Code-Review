        if (menuListener != null) {
            getParentMenuManager().removeMenuListener(menuListener);
            menuListener = null;
            menuListeners.clear();
        }
        if (mapMenuToWrapper != null) {
            Iterator<SubMenuManager> iter = mapMenuToWrapper.values().iterator();
            while (iter.hasNext()) {
                SubMenuManager wrapper = iter.next();
                wrapper.disposeManager();
            }
            mapMenuToWrapper.clear();
            mapMenuToWrapper = null;
        }
        super.disposeManager();
    }

    @Override
