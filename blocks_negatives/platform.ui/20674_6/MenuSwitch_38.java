			case MenuPackageImpl.RENDERED_MENU: {
				MRenderedMenu renderedMenu = (MRenderedMenu)theEObject;
				T1 result = caseRenderedMenu(renderedMenu);
				if (result == null) result = caseMenu(renderedMenu);
				if (result == null) result = caseMenuElement(renderedMenu);
				if (result == null) result = caseElementContainer(renderedMenu);
				if (result == null) result = caseUIElement(renderedMenu);
				if (result == null) result = caseUILabel(renderedMenu);
				if (result == null) result = caseApplicationElement(renderedMenu);
				if (result == null) result = caseLocalizable(renderedMenu);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MenuPackageImpl.RENDERED_TOOL_BAR: {
				MRenderedToolBar renderedToolBar = (MRenderedToolBar)theEObject;
				T1 result = caseRenderedToolBar(renderedToolBar);
				if (result == null) result = caseToolBar(renderedToolBar);
				if (result == null) result = caseElementContainer(renderedToolBar);
				if (result == null) result = caseTrimElement(renderedToolBar);
				if (result == null) result = caseUIElement(renderedToolBar);
				if (result == null) result = caseApplicationElement(renderedToolBar);
				if (result == null) result = caseLocalizable(renderedToolBar);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
