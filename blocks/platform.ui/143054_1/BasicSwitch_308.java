			if (result == null)
				result = caseUIElement(partSashContainerElement);
			if (result == null)
				result = caseApplicationElement(partSashContainerElement);
			if (result == null)
				result = caseLocalizable(partSashContainerElement);
			if (result == null)
				result = defaultCase(theEObject);
