			if (result == null)
				result = caseGenericTile(partSashContainer);
			if (result == null)
				result = casePartSashContainerElement(partSashContainer);
			if (result == null)
				result = caseWindowElement(partSashContainer);
			if (result == null)
				result = caseElementContainer(partSashContainer);
			if (result == null)
				result = caseUIElement(partSashContainer);
			if (result == null)
				result = caseApplicationElement(partSashContainer);
			if (result == null)
				result = caseLocalizable(partSashContainer);
			if (result == null)
				result = defaultCase(theEObject);
