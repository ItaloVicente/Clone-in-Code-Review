		case UiPackageImpl.CONTEXT: {
			MContext context = (MContext) theEObject;
			T1 result = caseContext(context);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.DIRTYABLE: {
			MDirtyable dirtyable = (MDirtyable) theEObject;
			T1 result = caseDirtyable(dirtyable);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.INPUT: {
			MInput input = (MInput) theEObject;
			T1 result = caseInput(input);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.UI_ELEMENT: {
			MUIElement uiElement = (MUIElement) theEObject;
			T1 result = caseUIElement(uiElement);
			if (result == null)
				result = caseApplicationElement(uiElement);
			if (result == null)
				result = caseLocalizable(uiElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.ELEMENT_CONTAINER: {
			MElementContainer<?> elementContainer = (MElementContainer<?>) theEObject;
			T1 result = caseElementContainer(elementContainer);
			if (result == null)
				result = caseUIElement(elementContainer);
			if (result == null)
				result = caseApplicationElement(elementContainer);
			if (result == null)
				result = caseLocalizable(elementContainer);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.UI_LABEL: {
			MUILabel uiLabel = (MUILabel) theEObject;
			T1 result = caseUILabel(uiLabel);
			if (result == null)
				result = caseLocalizable(uiLabel);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.GENERIC_STACK: {
			MGenericStack<?> genericStack = (MGenericStack<?>) theEObject;
			T1 result = caseGenericStack(genericStack);
			if (result == null)
				result = caseElementContainer(genericStack);
			if (result == null)
				result = caseUIElement(genericStack);
			if (result == null)
				result = caseApplicationElement(genericStack);
			if (result == null)
				result = caseLocalizable(genericStack);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.GENERIC_TILE: {
			MGenericTile<?> genericTile = (MGenericTile<?>) theEObject;
			T1 result = caseGenericTile(genericTile);
			if (result == null)
				result = caseElementContainer(genericTile);
			if (result == null)
				result = caseUIElement(genericTile);
			if (result == null)
				result = caseApplicationElement(genericTile);
			if (result == null)
				result = caseLocalizable(genericTile);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.GENERIC_TRIM_CONTAINER: {
			MGenericTrimContainer<?> genericTrimContainer = (MGenericTrimContainer<?>) theEObject;
			T1 result = caseGenericTrimContainer(genericTrimContainer);
			if (result == null)
				result = caseElementContainer(genericTrimContainer);
			if (result == null)
				result = caseUIElement(genericTrimContainer);
			if (result == null)
				result = caseApplicationElement(genericTrimContainer);
			if (result == null)
				result = caseLocalizable(genericTrimContainer);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.EXPRESSION: {
			MExpression expression = (MExpression) theEObject;
			T1 result = caseExpression(expression);
			if (result == null)
				result = caseApplicationElement(expression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.CORE_EXPRESSION: {
			MCoreExpression coreExpression = (MCoreExpression) theEObject;
			T1 result = caseCoreExpression(coreExpression);
			if (result == null)
				result = caseExpression(coreExpression);
			if (result == null)
				result = caseApplicationElement(coreExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.IMPERATIVE_EXPRESSION: {
			MImperativeExpression imperativeExpression = (MImperativeExpression) theEObject;
			T1 result = caseImperativeExpression(imperativeExpression);
			if (result == null)
				result = caseExpression(imperativeExpression);
			if (result == null)
				result = caseContribution(imperativeExpression);
			if (result == null)
				result = caseApplicationElement(imperativeExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.SNIPPET_CONTAINER: {
			MSnippetContainer snippetContainer = (MSnippetContainer) theEObject;
			T1 result = caseSnippetContainer(snippetContainer);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case UiPackageImpl.LOCALIZABLE: {
			MLocalizable localizable = (MLocalizable) theEObject;
			T1 result = caseLocalizable(localizable);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
