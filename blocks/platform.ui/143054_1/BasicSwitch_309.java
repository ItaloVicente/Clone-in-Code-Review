			if (result == null)
				result = caseUIElement(windowElement);
			if (result == null)
				result = caseApplicationElement(windowElement);
			if (result == null)
				result = caseLocalizable(windowElement);
			if (result == null)
				result = defaultCase(theEObject);
