			case BasicPackageImpl.INPUT_PART: {
				MInputPart inputPart = (MInputPart)theEObject;
				T1 result = caseInputPart(inputPart);
				if (result == null) result = casePart(inputPart);
				if (result == null) result = caseInput(inputPart);
				if (result == null) result = casePartSashContainerElement(inputPart);
				if (result == null) result = caseStackElement(inputPart);
				if (result == null) result = caseContribution(inputPart);
				if (result == null) result = caseContext(inputPart);
				if (result == null) result = caseUILabel(inputPart);
				if (result == null) result = caseHandlerContainer(inputPart);
				if (result == null) result = caseDirtyable(inputPart);
				if (result == null) result = caseBindings(inputPart);
				if (result == null) result = caseWindowElement(inputPart);
				if (result == null) result = caseUIElement(inputPart);
				if (result == null) result = caseApplicationElement(inputPart);
				if (result == null) result = caseLocalizable(inputPart);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
