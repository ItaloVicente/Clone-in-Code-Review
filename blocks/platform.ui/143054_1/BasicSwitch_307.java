			if (result == null)
				result = caseUIElement(trimElement);
			if (result == null)
				result = caseApplicationElement(trimElement);
			if (result == null)
				result = caseLocalizable(trimElement);
			if (result == null)
				result = defaultCase(theEObject);
