			if (result == null) result = caseGenericTrimContainer(trimBar);
			if (result == null) result = caseElementContainer(trimBar);
			if (result == null) result = caseUIElement(trimBar);
			if (result == null) result = caseApplicationElement(trimBar);
			if (result == null) result = caseLocalizable(trimBar);
			if (result == null) result = defaultCase(theEObject);
