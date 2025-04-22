		case BasicPackageImpl.PART: {
			MPart part = (MPart) theEObject;
			T1 result = casePart(part);
			if (result == null)
				result = casePartSashContainerElement(part);
			if (result == null)
				result = caseStackElement(part);
			if (result == null)
				result = caseContribution(part);
			if (result == null)
				result = caseContext(part);
			if (result == null)
				result = caseUILabel(part);
			if (result == null)
				result = caseHandlerContainer(part);
			if (result == null)
				result = caseDirtyable(part);
			if (result == null)
				result = caseBindings(part);
			if (result == null)
				result = caseWindowElement(part);
			if (result == null)
				result = caseUIElement(part);
			if (result == null)
				result = caseApplicationElement(part);
			if (result == null)
				result = caseLocalizable(part);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.COMPOSITE_PART: {
			MCompositePart compositePart = (MCompositePart) theEObject;
			T1 result = caseCompositePart(compositePart);
			if (result == null)
				result = casePart(compositePart);
			if (result == null)
				result = caseGenericTile(compositePart);
			if (result == null)
				result = casePartSashContainerElement(compositePart);
			if (result == null)
				result = caseStackElement(compositePart);
			if (result == null)
				result = caseContribution(compositePart);
			if (result == null)
				result = caseContext(compositePart);
			if (result == null)
				result = caseUILabel(compositePart);
			if (result == null)
				result = caseHandlerContainer(compositePart);
			if (result == null)
				result = caseDirtyable(compositePart);
			if (result == null)
				result = caseBindings(compositePart);
			if (result == null)
				result = caseWindowElement(compositePart);
			if (result == null)
				result = caseElementContainer(compositePart);
			if (result == null)
				result = caseUIElement(compositePart);
			if (result == null)
				result = caseApplicationElement(compositePart);
			if (result == null)
				result = caseLocalizable(compositePart);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.INPUT_PART: {
			MInputPart inputPart = (MInputPart) theEObject;
			T1 result = caseInputPart(inputPart);
			if (result == null)
				result = casePart(inputPart);
			if (result == null)
				result = caseInput(inputPart);
			if (result == null)
				result = casePartSashContainerElement(inputPart);
			if (result == null)
				result = caseStackElement(inputPart);
			if (result == null)
				result = caseContribution(inputPart);
			if (result == null)
				result = caseContext(inputPart);
			if (result == null)
				result = caseUILabel(inputPart);
			if (result == null)
				result = caseHandlerContainer(inputPart);
			if (result == null)
				result = caseDirtyable(inputPart);
			if (result == null)
				result = caseBindings(inputPart);
			if (result == null)
				result = caseWindowElement(inputPart);
			if (result == null)
				result = caseUIElement(inputPart);
			if (result == null)
				result = caseApplicationElement(inputPart);
			if (result == null)
				result = caseLocalizable(inputPart);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.PART_STACK: {
			MPartStack partStack = (MPartStack) theEObject;
			T1 result = casePartStack(partStack);
			if (result == null)
				result = caseGenericStack(partStack);
			if (result == null)
				result = casePartSashContainerElement(partStack);
			if (result == null)
				result = caseWindowElement(partStack);
			if (result == null)
				result = caseElementContainer(partStack);
			if (result == null)
				result = caseUIElement(partStack);
			if (result == null)
				result = caseApplicationElement(partStack);
			if (result == null)
				result = caseLocalizable(partStack);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.PART_SASH_CONTAINER: {
			MPartSashContainer partSashContainer = (MPartSashContainer) theEObject;
			T1 result = casePartSashContainer(partSashContainer);
			if (result == null)
				result = caseGenericTile(partSashContainer);
			if (result == null)
				result = casePartSashContainerElement(partSashContainer);
			if (result == null)
				result = caseWindowElement(partSashContainer);
			if (result == null)
				result = caseElementContainer(partSashContainer);
			if (result == null)
				result = caseUIElement(partSashContainer);
			if (result == null)
				result = caseApplicationElement(partSashContainer);
			if (result == null)
				result = caseLocalizable(partSashContainer);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.WINDOW: {
			MWindow window = (MWindow) theEObject;
			T1 result = caseWindow(window);
			if (result == null)
				result = caseElementContainer(window);
			if (result == null)
				result = caseUILabel(window);
			if (result == null)
				result = caseContext(window);
			if (result == null)
				result = caseHandlerContainer(window);
			if (result == null)
				result = caseBindings(window);
			if (result == null)
				result = caseSnippetContainer(window);
			if (result == null)
				result = caseUIElement(window);
			if (result == null)
				result = caseApplicationElement(window);
			if (result == null)
				result = caseLocalizable(window);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.TRIMMED_WINDOW: {
			MTrimmedWindow trimmedWindow = (MTrimmedWindow) theEObject;
			T1 result = caseTrimmedWindow(trimmedWindow);
			if (result == null)
				result = caseWindow(trimmedWindow);
			if (result == null)
				result = caseElementContainer(trimmedWindow);
			if (result == null)
				result = caseUILabel(trimmedWindow);
			if (result == null)
				result = caseContext(trimmedWindow);
			if (result == null)
				result = caseHandlerContainer(trimmedWindow);
			if (result == null)
				result = caseBindings(trimmedWindow);
			if (result == null)
				result = caseSnippetContainer(trimmedWindow);
			if (result == null)
				result = caseUIElement(trimmedWindow);
			if (result == null)
				result = caseApplicationElement(trimmedWindow);
			if (result == null)
				result = caseLocalizable(trimmedWindow);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.TRIM_ELEMENT: {
			MTrimElement trimElement = (MTrimElement) theEObject;
			T1 result = caseTrimElement(trimElement);
			if (result == null)
				result = caseUIElement(trimElement);
			if (result == null)
				result = caseApplicationElement(trimElement);
			if (result == null)
				result = caseLocalizable(trimElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.PART_SASH_CONTAINER_ELEMENT: {
			MPartSashContainerElement partSashContainerElement = (MPartSashContainerElement) theEObject;
			T1 result = casePartSashContainerElement(partSashContainerElement);
			if (result == null)
				result = caseUIElement(partSashContainerElement);
			if (result == null)
				result = caseApplicationElement(partSashContainerElement);
			if (result == null)
				result = caseLocalizable(partSashContainerElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.WINDOW_ELEMENT: {
			MWindowElement windowElement = (MWindowElement) theEObject;
			T1 result = caseWindowElement(windowElement);
			if (result == null)
				result = caseUIElement(windowElement);
			if (result == null)
				result = caseApplicationElement(windowElement);
			if (result == null)
				result = caseLocalizable(windowElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.TRIM_BAR: {
			MTrimBar trimBar = (MTrimBar) theEObject;
			T1 result = caseTrimBar(trimBar);
			if (result == null)
				result = caseGenericTrimContainer(trimBar);
			if (result == null)
				result = caseElementContainer(trimBar);
			if (result == null)
				result = caseUIElement(trimBar);
			if (result == null)
				result = caseApplicationElement(trimBar);
			if (result == null)
				result = caseLocalizable(trimBar);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.STACK_ELEMENT: {
			MStackElement stackElement = (MStackElement) theEObject;
			T1 result = caseStackElement(stackElement);
			if (result == null)
				result = caseUIElement(stackElement);
			if (result == null)
				result = caseApplicationElement(stackElement);
			if (result == null)
				result = caseLocalizable(stackElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.DIALOG: {
			MDialog dialog = (MDialog) theEObject;
			T1 result = caseDialog(dialog);
			if (result == null)
				result = caseWindow(dialog);
			if (result == null)
				result = caseElementContainer(dialog);
			if (result == null)
				result = caseUILabel(dialog);
			if (result == null)
				result = caseContext(dialog);
			if (result == null)
				result = caseHandlerContainer(dialog);
			if (result == null)
				result = caseBindings(dialog);
			if (result == null)
				result = caseSnippetContainer(dialog);
			if (result == null)
				result = caseUIElement(dialog);
			if (result == null)
				result = caseApplicationElement(dialog);
			if (result == null)
				result = caseLocalizable(dialog);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.WIZARD_DIALOG: {
			MWizardDialog wizardDialog = (MWizardDialog) theEObject;
			T1 result = caseWizardDialog(wizardDialog);
			if (result == null)
				result = caseDialog(wizardDialog);
			if (result == null)
				result = caseWindow(wizardDialog);
			if (result == null)
				result = caseElementContainer(wizardDialog);
			if (result == null)
				result = caseUILabel(wizardDialog);
			if (result == null)
				result = caseContext(wizardDialog);
			if (result == null)
				result = caseHandlerContainer(wizardDialog);
			if (result == null)
				result = caseBindings(wizardDialog);
			if (result == null)
				result = caseSnippetContainer(wizardDialog);
			if (result == null)
				result = caseUIElement(wizardDialog);
			if (result == null)
				result = caseApplicationElement(wizardDialog);
			if (result == null)
				result = caseLocalizable(wizardDialog);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
