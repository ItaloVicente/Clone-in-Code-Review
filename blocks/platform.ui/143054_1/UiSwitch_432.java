			if (result == null)
				result = caseElementContainer(genericTrimContainer);
			if (result == null)
				result = caseUIElement(genericTrimContainer);
			if (result == null)
				result = caseApplicationElement(genericTrimContainer);
			if (result == null)
				result = caseLocalizable(genericTrimContainer);
			if (result == null)
				result = defaultCase(theEObject);
