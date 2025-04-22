			case FragmentPackageImpl.MODEL_FRAGMENTS: {
				MModelFragments modelFragments = (MModelFragments)theEObject;
				T result = caseModelFragments(modelFragments);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FragmentPackageImpl.MODEL_FRAGMENT: {
				MModelFragment modelFragment = (MModelFragment)theEObject;
				T result = caseModelFragment(modelFragment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FragmentPackageImpl.STRING_MODEL_FRAGMENT: {
				MStringModelFragment stringModelFragment = (MStringModelFragment)theEObject;
				T result = caseStringModelFragment(stringModelFragment);
				if (result == null) result = caseModelFragment(stringModelFragment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
