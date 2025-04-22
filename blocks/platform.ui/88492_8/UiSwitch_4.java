			case UiPackageImpl.IMPERATIVE_EXPRESSION: {
				MImperativeExpression imperativeExpression = (MImperativeExpression)theEObject;
				T1 result = caseImperativeExpression(imperativeExpression);
				if (result == null) result = caseExpression(imperativeExpression);
				if (result == null) result = caseContribution(imperativeExpression);
				if (result == null) result = caseApplicationElement(imperativeExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
