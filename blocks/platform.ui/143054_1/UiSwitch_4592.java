			if (result == null)
				result = caseApplicationElement(uiElement);
			if (result == null)
				result = caseLocalizable(uiElement);
			if (result == null)
				result = defaultCase(theEObject);
