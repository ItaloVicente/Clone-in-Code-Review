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
