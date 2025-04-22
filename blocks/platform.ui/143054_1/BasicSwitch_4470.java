			if (result == null)
				result = caseWindow(trimmedWindow);
			if (result == null)
				result = caseElementContainer(trimmedWindow);
			if (result == null)
				result = caseUILabel(trimmedWindow);
			if (result == null)
				result = caseContext(trimmedWindow);
			if (result == null)
				result = caseHandlerContainer(trimmedWindow);
			if (result == null)
				result = caseBindings(trimmedWindow);
			if (result == null)
				result = caseSnippetContainer(trimmedWindow);
			if (result == null)
				result = caseUIElement(trimmedWindow);
			if (result == null)
				result = caseApplicationElement(trimmedWindow);
			if (result == null)
				result = caseLocalizable(trimmedWindow);
			if (result == null)
				result = defaultCase(theEObject);
