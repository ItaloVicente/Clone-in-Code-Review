		case MenuPackageImpl.MENU_SEPARATOR:
			return (EObject) createMenuSeparator();
		case MenuPackageImpl.MENU:
			return (EObject) createMenu();
		case MenuPackageImpl.MENU_CONTRIBUTION:
			return (EObject) createMenuContribution();
		case MenuPackageImpl.POPUP_MENU:
			return (EObject) createPopupMenu();
		case MenuPackageImpl.DIRECT_MENU_ITEM:
			return (EObject) createDirectMenuItem();
		case MenuPackageImpl.HANDLED_MENU_ITEM:
			return (EObject) createHandledMenuItem();
		case MenuPackageImpl.TOOL_BAR:
			return (EObject) createToolBar();
		case MenuPackageImpl.TOOL_CONTROL:
			return (EObject) createToolControl();
		case MenuPackageImpl.HANDLED_TOOL_ITEM:
			return (EObject) createHandledToolItem();
		case MenuPackageImpl.DIRECT_TOOL_ITEM:
			return (EObject) createDirectToolItem();
		case MenuPackageImpl.TOOL_BAR_SEPARATOR:
			return (EObject) createToolBarSeparator();
		case MenuPackageImpl.TOOL_BAR_CONTRIBUTION:
			return (EObject) createToolBarContribution();
		case MenuPackageImpl.TRIM_CONTRIBUTION:
			return (EObject) createTrimContribution();
		case MenuPackageImpl.DYNAMIC_MENU_CONTRIBUTION:
			return (EObject) createDynamicMenuContribution();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
