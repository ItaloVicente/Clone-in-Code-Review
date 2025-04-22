			case BasicPackageImpl.DIALOG: {
				MDialog dialog = (MDialog)theEObject;
				T1 result = caseDialog(dialog);
				if (result == null) result = caseWindow(dialog);
				if (result == null) result = caseElementContainer(dialog);
				if (result == null) result = caseUILabel(dialog);
				if (result == null) result = caseContext(dialog);
				if (result == null) result = caseHandlerContainer(dialog);
				if (result == null) result = caseBindings(dialog);
				if (result == null) result = caseSnippetContainer(dialog);
				if (result == null) result = caseUIElement(dialog);
				if (result == null) result = caseApplicationElement(dialog);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasicPackageImpl.WIZARD_DIALOG: {
				MWizardDialog wizardDialog = (MWizardDialog)theEObject;
				T1 result = caseWizardDialog(wizardDialog);
				if (result == null) result = caseDialog(wizardDialog);
				if (result == null) result = caseWindow(wizardDialog);
				if (result == null) result = caseElementContainer(wizardDialog);
				if (result == null) result = caseUILabel(wizardDialog);
				if (result == null) result = caseContext(wizardDialog);
				if (result == null) result = caseHandlerContainer(wizardDialog);
				if (result == null) result = caseBindings(wizardDialog);
				if (result == null) result = caseSnippetContainer(wizardDialog);
				if (result == null) result = caseUIElement(wizardDialog);
				if (result == null) result = caseApplicationElement(wizardDialog);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
