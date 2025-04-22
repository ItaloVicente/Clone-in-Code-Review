			if (UIEvents.UIElement.TOBERENDERED.equals(attName)) {
				boolean tbr = (Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
				if (!tbr) {
					List<MMenu> menus = part.getMenus();
					for (MMenu menu : menus) {
						if (menu instanceof MPopupMenu)
							unlinkMenu(menu);
					}
