			if (result == null)
				result = caseWindow(dialog);
			if (result == null)
				result = caseElementContainer(dialog);
			if (result == null)
				result = caseUILabel(dialog);
			if (result == null)
				result = caseContext(dialog);
			if (result == null)
				result = caseHandlerContainer(dialog);
			if (result == null)
				result = caseBindings(dialog);
			if (result == null)
				result = caseSnippetContainer(dialog);
			if (result == null)
				result = caseUIElement(dialog);
			if (result == null)
				result = caseApplicationElement(dialog);
			if (result == null)
				result = caseLocalizable(dialog);
			if (result == null)
				result = defaultCase(theEObject);
