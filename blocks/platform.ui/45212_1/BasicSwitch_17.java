			case BasicPackageImpl.DIALOG_ELEMENT: {
				MDialogElement dialogElement = (MDialogElement)theEObject;
				T1 result = caseDialogElement(dialogElement);
				if (result == null) result = caseUIElement(dialogElement);
				if (result == null) result = caseApplicationElement(dialogElement);
				if (result == null) result = caseLocalizable(dialogElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasicPackageImpl.WIZARD_ELEMENT: {
				MWizardElement wizardElement = (MWizardElement)theEObject;
				T1 result = caseWizardElement(wizardElement);
				if (result == null) result = caseUIElement(wizardElement);
				if (result == null) result = caseApplicationElement(wizardElement);
				if (result == null) result = caseLocalizable(wizardElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
