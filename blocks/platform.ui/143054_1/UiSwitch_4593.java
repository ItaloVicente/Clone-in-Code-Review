			if (result == null)
				result = caseUIElement(elementContainer);
			if (result == null)
				result = caseApplicationElement(elementContainer);
			if (result == null)
				result = caseLocalizable(elementContainer);
			if (result == null)
				result = defaultCase(theEObject);
