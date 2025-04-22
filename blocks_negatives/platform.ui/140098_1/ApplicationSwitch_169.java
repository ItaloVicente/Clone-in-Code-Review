			case ApplicationPackageImpl.STRING_TO_STRING_MAP: {
				@SuppressWarnings("unchecked") Map.Entry<String, String> stringToStringMap = (Map.Entry<String, String>)theEObject;
				T1 result = caseStringToStringMap(stringToStringMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackageImpl.APPLICATION: {
				MApplication application = (MApplication)theEObject;
				T1 result = caseApplication(application);
				if (result == null) result = caseElementContainer(application);
				if (result == null) result = caseContext(application);
				if (result == null) result = caseHandlerContainer(application);
				if (result == null) result = caseBindingTableContainer(application);
				if (result == null) result = casePartDescriptorContainer(application);
				if (result == null) result = caseBindings(application);
				if (result == null) result = caseMenuContributions(application);
				if (result == null) result = caseToolBarContributions(application);
				if (result == null) result = caseTrimContributions(application);
				if (result == null) result = caseSnippetContainer(application);
				if (result == null) result = caseUIElement(application);
				if (result == null) result = caseApplicationElement(application);
				if (result == null) result = caseLocalizable(application);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackageImpl.APPLICATION_ELEMENT: {
				MApplicationElement applicationElement = (MApplicationElement)theEObject;
				T1 result = caseApplicationElement(applicationElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackageImpl.CONTRIBUTION: {
				MContribution contribution = (MContribution)theEObject;
				T1 result = caseContribution(contribution);
				if (result == null) result = caseApplicationElement(contribution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackageImpl.ADDON: {
				MAddon addon = (MAddon)theEObject;
				T1 result = caseAddon(addon);
				if (result == null) result = caseContribution(addon);
				if (result == null) result = caseApplicationElement(addon);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackageImpl.STRING_TO_OBJECT_MAP: {
				@SuppressWarnings("unchecked") Map.Entry<String, Object> stringToObjectMap = (Map.Entry<String, Object>)theEObject;
				T1 result = caseStringToObjectMap(stringToObjectMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
