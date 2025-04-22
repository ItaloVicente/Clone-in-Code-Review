			case UiPackageImpl.LOCALIZABLE: {
				MLocalizable localizable = (MLocalizable)theEObject;
				T1 result = caseLocalizable(localizable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
