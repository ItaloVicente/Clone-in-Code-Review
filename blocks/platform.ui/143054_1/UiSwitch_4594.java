			if (result == null)
				result = caseElementContainer(genericStack);
			if (result == null)
				result = caseUIElement(genericStack);
			if (result == null)
				result = caseApplicationElement(genericStack);
			if (result == null)
				result = caseLocalizable(genericStack);
			if (result == null)
				result = defaultCase(theEObject);
