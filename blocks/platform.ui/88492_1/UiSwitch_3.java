			case UiPackageImpl.JAVA_EXPRESSION: {
				MJavaExpression javaExpression = (MJavaExpression)theEObject;
				T1 result = caseJavaExpression(javaExpression);
				if (result == null) result = caseExpression(javaExpression);
				if (result == null) result = caseContribution(javaExpression);
				if (result == null) result = caseApplicationElement(javaExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
