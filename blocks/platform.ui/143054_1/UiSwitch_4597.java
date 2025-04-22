			if (result == null)
				result = caseExpression(coreExpression);
			if (result == null)
				result = caseApplicationElement(coreExpression);
			if (result == null)
				result = defaultCase(theEObject);
