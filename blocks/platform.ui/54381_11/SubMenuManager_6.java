            menuListener = manager -> {
				Object[] listeners = menuListeners.getListeners();
				for (Object localListener : listeners) {
					((IMenuListener) localListener).menuAboutToShow(SubMenuManager.this);
			    }
			};
