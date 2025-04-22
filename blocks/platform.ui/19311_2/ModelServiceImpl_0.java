
			MMenu menu = window.getMainMenu();
			if (menu != null && (searchFlags & IN_MAIN_MENU) != 0
					&& isTypeSupported(type, MMenuElement.class)) {
				findElementsRecursive(menu, id, type, tagsToMatch, elements, searchFlags);
			}
