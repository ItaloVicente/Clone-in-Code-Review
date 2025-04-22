		case AdvancedPackageImpl.PLACEHOLDER: {
			MPlaceholder placeholder = (MPlaceholder) theEObject;
			T1 result = casePlaceholder(placeholder);
			if (result == null)
				result = casePartSashContainerElement(placeholder);
			if (result == null)
				result = caseStackElement(placeholder);
			if (result == null)
				result = caseUIElement(placeholder);
			if (result == null)
				result = caseApplicationElement(placeholder);
			if (result == null)
				result = caseLocalizable(placeholder);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AdvancedPackageImpl.PERSPECTIVE: {
			MPerspective perspective = (MPerspective) theEObject;
			T1 result = casePerspective(perspective);
			if (result == null)
				result = caseElementContainer(perspective);
			if (result == null)
				result = caseUILabel(perspective);
			if (result == null)
				result = caseContext(perspective);
			if (result == null)
				result = caseHandlerContainer(perspective);
			if (result == null)
				result = caseBindings(perspective);
			if (result == null)
				result = caseUIElement(perspective);
			if (result == null)
				result = caseApplicationElement(perspective);
			if (result == null)
				result = caseLocalizable(perspective);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AdvancedPackageImpl.PERSPECTIVE_STACK: {
			MPerspectiveStack perspectiveStack = (MPerspectiveStack) theEObject;
			T1 result = casePerspectiveStack(perspectiveStack);
			if (result == null)
				result = caseGenericStack(perspectiveStack);
			if (result == null)
				result = casePartSashContainerElement(perspectiveStack);
			if (result == null)
				result = caseWindowElement(perspectiveStack);
			if (result == null)
				result = caseApplicationElement(perspectiveStack);
			if (result == null)
				result = caseLocalizable(perspectiveStack);
			if (result == null)
				result = caseElementContainer(perspectiveStack);
			if (result == null)
				result = caseUIElement(perspectiveStack);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AdvancedPackageImpl.AREA: {
			MArea area = (MArea) theEObject;
			T1 result = caseArea(area);
			if (result == null)
				result = casePartSashContainer(area);
			if (result == null)
				result = caseUILabel(area);
			if (result == null)
				result = caseGenericTile(area);
			if (result == null)
				result = casePartSashContainerElement(area);
			if (result == null)
				result = caseWindowElement(area);
			if (result == null)
				result = caseElementContainer(area);
			if (result == null)
				result = caseUIElement(area);
			if (result == null)
				result = caseApplicationElement(area);
			if (result == null)
				result = caseLocalizable(area);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
