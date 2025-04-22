			if (result == null)
				result = caseElementContainer(genericTile);
			if (result == null)
				result = caseUIElement(genericTile);
			if (result == null)
				result = caseApplicationElement(genericTile);
			if (result == null)
				result = caseLocalizable(genericTile);
			if (result == null)
				result = defaultCase(theEObject);
