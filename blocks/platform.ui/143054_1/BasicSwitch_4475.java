			if (result == null)
				result = caseUIElement(stackElement);
			if (result == null)
				result = caseApplicationElement(stackElement);
			if (result == null)
				result = caseLocalizable(stackElement);
			if (result == null)
				result = defaultCase(theEObject);
