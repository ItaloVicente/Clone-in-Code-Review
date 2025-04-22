        super.removeAll();
        if (mapMenuToWrapper != null) {
            Iterator<SubMenuManager> iter = mapMenuToWrapper.values().iterator();
            while (iter.hasNext()) {
                SubMenuManager wrapper = iter.next();
                wrapper.removeAll();
            }
            mapMenuToWrapper.clear();
            mapMenuToWrapper = null;
        }
    }

    @Override
