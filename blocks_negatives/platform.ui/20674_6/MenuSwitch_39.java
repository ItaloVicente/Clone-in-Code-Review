			case MenuPackageImpl.RENDERED_MENU_ITEM: {
				MRenderedMenuItem renderedMenuItem = (MRenderedMenuItem)theEObject;
				T1 result = caseRenderedMenuItem(renderedMenuItem);
				if (result == null) result = caseMenuItem(renderedMenuItem);
				if (result == null) result = caseItem(renderedMenuItem);
				if (result == null) result = caseMenuElement(renderedMenuItem);
				if (result == null) result = caseUIElement(renderedMenuItem);
				if (result == null) result = caseUILabel(renderedMenuItem);
				if (result == null) result = caseApplicationElement(renderedMenuItem);
				if (result == null) result = caseLocalizable(renderedMenuItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MenuPackageImpl.OPAQUE_TOOL_ITEM: {
				MOpaqueToolItem opaqueToolItem = (MOpaqueToolItem)theEObject;
				T1 result = caseOpaqueToolItem(opaqueToolItem);
				if (result == null) result = caseToolItem(opaqueToolItem);
				if (result == null) result = caseItem(opaqueToolItem);
				if (result == null) result = caseToolBarElement(opaqueToolItem);
				if (result == null) result = caseUIElement(opaqueToolItem);
				if (result == null) result = caseUILabel(opaqueToolItem);
				if (result == null) result = caseApplicationElement(opaqueToolItem);
				if (result == null) result = caseLocalizable(opaqueToolItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MenuPackageImpl.OPAQUE_MENU_ITEM: {
				MOpaqueMenuItem opaqueMenuItem = (MOpaqueMenuItem)theEObject;
				T1 result = caseOpaqueMenuItem(opaqueMenuItem);
				if (result == null) result = caseMenuItem(opaqueMenuItem);
				if (result == null) result = caseItem(opaqueMenuItem);
				if (result == null) result = caseMenuElement(opaqueMenuItem);
				if (result == null) result = caseUIElement(opaqueMenuItem);
				if (result == null) result = caseUILabel(opaqueMenuItem);
				if (result == null) result = caseApplicationElement(opaqueMenuItem);
				if (result == null) result = caseLocalizable(opaqueMenuItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MenuPackageImpl.OPAQUE_MENU_SEPARATOR: {
				MOpaqueMenuSeparator opaqueMenuSeparator = (MOpaqueMenuSeparator)theEObject;
				T1 result = caseOpaqueMenuSeparator(opaqueMenuSeparator);
				if (result == null) result = caseMenuSeparator(opaqueMenuSeparator);
				if (result == null) result = caseMenuElement(opaqueMenuSeparator);
				if (result == null) result = caseUIElement(opaqueMenuSeparator);
				if (result == null) result = caseUILabel(opaqueMenuSeparator);
				if (result == null) result = caseApplicationElement(opaqueMenuSeparator);
				if (result == null) result = caseLocalizable(opaqueMenuSeparator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MenuPackageImpl.OPAQUE_MENU: {
				MOpaqueMenu opaqueMenu = (MOpaqueMenu)theEObject;
				T1 result = caseOpaqueMenu(opaqueMenu);
				if (result == null) result = caseMenu(opaqueMenu);
				if (result == null) result = caseMenuElement(opaqueMenu);
				if (result == null) result = caseElementContainer(opaqueMenu);
				if (result == null) result = caseUIElement(opaqueMenu);
				if (result == null) result = caseUILabel(opaqueMenu);
				if (result == null) result = caseApplicationElement(opaqueMenu);
				if (result == null) result = caseLocalizable(opaqueMenu);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
