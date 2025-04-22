            menuListener = new IMenuListener() {
                @Override
				public void menuAboutToShow(IMenuManager manager) {
                    Object[] listeners = menuListeners.getListeners();
                    for (int i = 0; i < listeners.length; ++i) {
                        ((IMenuListener) listeners[i])
                                .menuAboutToShow(SubMenuManager.this);
                    }
                }
            };
