			if (result == null)
				result = caseElementContainer(window);
			if (result == null)
				result = caseUILabel(window);
			if (result == null)
				result = caseContext(window);
			if (result == null)
				result = caseHandlerContainer(window);
			if (result == null)
				result = caseBindings(window);
			if (result == null)
				result = caseSnippetContainer(window);
			if (result == null)
				result = caseUIElement(window);
			if (result == null)
				result = caseApplicationElement(window);
			if (result == null)
				result = caseLocalizable(window);
			if (result == null)
				result = defaultCase(theEObject);
