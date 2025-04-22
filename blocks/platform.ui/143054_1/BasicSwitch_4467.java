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
