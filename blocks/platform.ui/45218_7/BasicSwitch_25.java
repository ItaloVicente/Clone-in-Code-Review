			case BasicPackageImpl.WIZARD: {
				MWizard wizard = (MWizard)theEObject;
				T1 result = caseWizard(wizard);
				if (result == null) result = caseUILabel(wizard);
				if (result == null) result = caseGenericStack(wizard);
				if (result == null) result = caseFrameElement(wizard);
				if (result == null) result = caseElementContainer(wizard);
				if (result == null) result = caseUIElement(wizard);
				if (result == null) result = caseLocalizable(wizard);
				if (result == null) result = caseApplicationElement(wizard);
