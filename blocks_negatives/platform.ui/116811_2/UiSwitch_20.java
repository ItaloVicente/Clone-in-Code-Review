			case UiPackageImpl.INPUT: {
				MInput input = (MInput)theEObject;
				T1 result = caseInput(input);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
