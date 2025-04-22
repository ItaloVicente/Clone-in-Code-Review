		case MenuPackageImpl.ITEM: {
			MItem item = (MItem) theEObject;
			T1 result = caseItem(item);
			if (result == null)
				result = caseUIElement(item);
			if (result == null)
				result = caseUILabel(item);
			if (result == null)
				result = caseApplicationElement(item);
			if (result == null)
				result = caseLocalizable(item);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.HANDLED_ITEM: {
			MHandledItem handledItem = (MHandledItem) theEObject;
			T1 result = caseHandledItem(handledItem);
			if (result == null)
				result = caseItem(handledItem);
			if (result == null)
				result = caseUIElement(handledItem);
			if (result == null)
				result = caseUILabel(handledItem);
			if (result == null)
				result = caseApplicationElement(handledItem);
			if (result == null)
				result = caseLocalizable(handledItem);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.MENU_ELEMENT: {
			MMenuElement menuElement = (MMenuElement) theEObject;
			T1 result = caseMenuElement(menuElement);
			if (result == null)
				result = caseUIElement(menuElement);
			if (result == null)
				result = caseUILabel(menuElement);
			if (result == null)
				result = caseApplicationElement(menuElement);
			if (result == null)
				result = caseLocalizable(menuElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.MENU_ITEM: {
			MMenuItem menuItem = (MMenuItem) theEObject;
			T1 result = caseMenuItem(menuItem);
			if (result == null)
				result = caseItem(menuItem);
			if (result == null)
				result = caseMenuElement(menuItem);
			if (result == null)
				result = caseUIElement(menuItem);
			if (result == null)
				result = caseUILabel(menuItem);
			if (result == null)
				result = caseApplicationElement(menuItem);
			if (result == null)
				result = caseLocalizable(menuItem);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.MENU_SEPARATOR: {
			MMenuSeparator menuSeparator = (MMenuSeparator) theEObject;
			T1 result = caseMenuSeparator(menuSeparator);
			if (result == null)
				result = caseMenuElement(menuSeparator);
			if (result == null)
				result = caseUIElement(menuSeparator);
			if (result == null)
				result = caseUILabel(menuSeparator);
			if (result == null)
				result = caseApplicationElement(menuSeparator);
			if (result == null)
				result = caseLocalizable(menuSeparator);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.MENU: {
			MMenu menu = (MMenu) theEObject;
			T1 result = caseMenu(menu);
			if (result == null)
				result = caseMenuElement(menu);
			if (result == null)
				result = caseElementContainer(menu);
			if (result == null)
				result = caseUIElement(menu);
			if (result == null)
				result = caseUILabel(menu);
			if (result == null)
				result = caseApplicationElement(menu);
			if (result == null)
				result = caseLocalizable(menu);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.MENU_CONTRIBUTION: {
			MMenuContribution menuContribution = (MMenuContribution) theEObject;
			T1 result = caseMenuContribution(menuContribution);
			if (result == null)
				result = caseElementContainer(menuContribution);
			if (result == null)
				result = caseUIElement(menuContribution);
			if (result == null)
				result = caseApplicationElement(menuContribution);
			if (result == null)
				result = caseLocalizable(menuContribution);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.POPUP_MENU: {
			MPopupMenu popupMenu = (MPopupMenu) theEObject;
			T1 result = casePopupMenu(popupMenu);
			if (result == null)
				result = caseMenu(popupMenu);
			if (result == null)
				result = caseContext(popupMenu);
			if (result == null)
				result = caseMenuElement(popupMenu);
			if (result == null)
				result = caseElementContainer(popupMenu);
			if (result == null)
				result = caseUIElement(popupMenu);
			if (result == null)
				result = caseUILabel(popupMenu);
			if (result == null)
				result = caseApplicationElement(popupMenu);
			if (result == null)
				result = caseLocalizable(popupMenu);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.DIRECT_MENU_ITEM: {
			MDirectMenuItem directMenuItem = (MDirectMenuItem) theEObject;
			T1 result = caseDirectMenuItem(directMenuItem);
			if (result == null)
				result = caseMenuItem(directMenuItem);
			if (result == null)
				result = caseContribution(directMenuItem);
			if (result == null)
				result = caseItem(directMenuItem);
			if (result == null)
				result = caseMenuElement(directMenuItem);
			if (result == null)
				result = caseUIElement(directMenuItem);
			if (result == null)
				result = caseUILabel(directMenuItem);
			if (result == null)
				result = caseApplicationElement(directMenuItem);
			if (result == null)
				result = caseLocalizable(directMenuItem);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.HANDLED_MENU_ITEM: {
			MHandledMenuItem handledMenuItem = (MHandledMenuItem) theEObject;
			T1 result = caseHandledMenuItem(handledMenuItem);
			if (result == null)
				result = caseMenuItem(handledMenuItem);
			if (result == null)
				result = caseHandledItem(handledMenuItem);
			if (result == null)
				result = caseItem(handledMenuItem);
			if (result == null)
				result = caseMenuElement(handledMenuItem);
			if (result == null)
				result = caseUIElement(handledMenuItem);
			if (result == null)
				result = caseUILabel(handledMenuItem);
			if (result == null)
				result = caseApplicationElement(handledMenuItem);
			if (result == null)
				result = caseLocalizable(handledMenuItem);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.TOOL_ITEM: {
			MToolItem toolItem = (MToolItem) theEObject;
			T1 result = caseToolItem(toolItem);
			if (result == null)
				result = caseItem(toolItem);
			if (result == null)
				result = caseToolBarElement(toolItem);
			if (result == null)
				result = caseUIElement(toolItem);
			if (result == null)
				result = caseUILabel(toolItem);
			if (result == null)
				result = caseApplicationElement(toolItem);
			if (result == null)
				result = caseLocalizable(toolItem);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.TOOL_BAR: {
			MToolBar toolBar = (MToolBar) theEObject;
			T1 result = caseToolBar(toolBar);
			if (result == null)
				result = caseElementContainer(toolBar);
			if (result == null)
				result = caseTrimElement(toolBar);
			if (result == null)
				result = caseUIElement(toolBar);
			if (result == null)
				result = caseApplicationElement(toolBar);
			if (result == null)
				result = caseLocalizable(toolBar);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.TOOL_BAR_ELEMENT: {
			MToolBarElement toolBarElement = (MToolBarElement) theEObject;
			T1 result = caseToolBarElement(toolBarElement);
			if (result == null)
				result = caseUIElement(toolBarElement);
			if (result == null)
				result = caseApplicationElement(toolBarElement);
			if (result == null)
				result = caseLocalizable(toolBarElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.TOOL_CONTROL: {
			MToolControl toolControl = (MToolControl) theEObject;
			T1 result = caseToolControl(toolControl);
			if (result == null)
				result = caseToolBarElement(toolControl);
			if (result == null)
				result = caseContribution(toolControl);
			if (result == null)
				result = caseTrimElement(toolControl);
			if (result == null)
				result = caseUIElement(toolControl);
			if (result == null)
				result = caseApplicationElement(toolControl);
			if (result == null)
				result = caseLocalizable(toolControl);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.HANDLED_TOOL_ITEM: {
			MHandledToolItem handledToolItem = (MHandledToolItem) theEObject;
			T1 result = caseHandledToolItem(handledToolItem);
			if (result == null)
				result = caseToolItem(handledToolItem);
			if (result == null)
				result = caseHandledItem(handledToolItem);
			if (result == null)
				result = caseItem(handledToolItem);
			if (result == null)
				result = caseToolBarElement(handledToolItem);
			if (result == null)
				result = caseUIElement(handledToolItem);
			if (result == null)
				result = caseUILabel(handledToolItem);
			if (result == null)
				result = caseApplicationElement(handledToolItem);
			if (result == null)
				result = caseLocalizable(handledToolItem);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.DIRECT_TOOL_ITEM: {
			MDirectToolItem directToolItem = (MDirectToolItem) theEObject;
			T1 result = caseDirectToolItem(directToolItem);
			if (result == null)
				result = caseToolItem(directToolItem);
			if (result == null)
				result = caseContribution(directToolItem);
			if (result == null)
				result = caseItem(directToolItem);
			if (result == null)
				result = caseToolBarElement(directToolItem);
			if (result == null)
				result = caseUIElement(directToolItem);
			if (result == null)
				result = caseUILabel(directToolItem);
			if (result == null)
				result = caseApplicationElement(directToolItem);
			if (result == null)
				result = caseLocalizable(directToolItem);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.TOOL_BAR_SEPARATOR: {
			MToolBarSeparator toolBarSeparator = (MToolBarSeparator) theEObject;
			T1 result = caseToolBarSeparator(toolBarSeparator);
			if (result == null)
				result = caseToolBarElement(toolBarSeparator);
			if (result == null)
				result = caseUIElement(toolBarSeparator);
			if (result == null)
				result = caseApplicationElement(toolBarSeparator);
			if (result == null)
				result = caseLocalizable(toolBarSeparator);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.MENU_CONTRIBUTIONS: {
			MMenuContributions menuContributions = (MMenuContributions) theEObject;
			T1 result = caseMenuContributions(menuContributions);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.TOOL_BAR_CONTRIBUTION: {
			MToolBarContribution toolBarContribution = (MToolBarContribution) theEObject;
			T1 result = caseToolBarContribution(toolBarContribution);
			if (result == null)
				result = caseElementContainer(toolBarContribution);
			if (result == null)
				result = caseUIElement(toolBarContribution);
			if (result == null)
				result = caseApplicationElement(toolBarContribution);
			if (result == null)
				result = caseLocalizable(toolBarContribution);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.TOOL_BAR_CONTRIBUTIONS: {
			MToolBarContributions toolBarContributions = (MToolBarContributions) theEObject;
			T1 result = caseToolBarContributions(toolBarContributions);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.TRIM_CONTRIBUTION: {
			MTrimContribution trimContribution = (MTrimContribution) theEObject;
			T1 result = caseTrimContribution(trimContribution);
			if (result == null)
				result = caseElementContainer(trimContribution);
			if (result == null)
				result = caseUIElement(trimContribution);
			if (result == null)
				result = caseApplicationElement(trimContribution);
			if (result == null)
				result = caseLocalizable(trimContribution);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.TRIM_CONTRIBUTIONS: {
			MTrimContributions trimContributions = (MTrimContributions) theEObject;
			T1 result = caseTrimContributions(trimContributions);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MenuPackageImpl.DYNAMIC_MENU_CONTRIBUTION: {
			MDynamicMenuContribution dynamicMenuContribution = (MDynamicMenuContribution) theEObject;
			T1 result = caseDynamicMenuContribution(dynamicMenuContribution);
			if (result == null)
				result = caseMenuItem(dynamicMenuContribution);
			if (result == null)
				result = caseContribution(dynamicMenuContribution);
			if (result == null)
				result = caseItem(dynamicMenuContribution);
			if (result == null)
				result = caseMenuElement(dynamicMenuContribution);
			if (result == null)
				result = caseUIElement(dynamicMenuContribution);
			if (result == null)
				result = caseUILabel(dynamicMenuContribution);
			if (result == null)
				result = caseApplicationElement(dynamicMenuContribution);
			if (result == null)
				result = caseLocalizable(dynamicMenuContribution);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
