			case BasicPackageImpl.WIZARD: {
				MWizard wizard = (MWizard)theEObject;
				T1 result = caseWizard(wizard);
				if (result == null) result = caseElementContainer(wizard);
				if (result == null) result = caseDialogElement(wizard);
				if (result == null) result = caseUIElement(wizard);
				if (result == null) result = caseApplicationElement(wizard);
				if (result == null) result = caseLocalizable(wizard);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
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
			case BasicPackageImpl.FRAME: {
				MFrame<?> frame = (MFrame<?>)theEObject;
				T1 result = caseFrame(frame);
				if (result == null) result = caseElementContainer(frame);
				if (result == null) result = caseUILabel(frame);
				if (result == null) result = caseContext(frame);
				if (result == null) result = caseHandlerContainer(frame);
				if (result == null) result = caseBindings(frame);
				if (result == null) result = caseUIElement(frame);
				if (result == null) result = caseApplicationElement(frame);
				if (result == null) result = caseLocalizable(frame);
