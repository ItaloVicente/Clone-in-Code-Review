		case BasicPackageImpl.PART:
			return (EObject) createPart();
		case BasicPackageImpl.COMPOSITE_PART:
			return (EObject) createCompositePart();
		case BasicPackageImpl.INPUT_PART:
			return (EObject) createInputPart();
		case BasicPackageImpl.PART_STACK:
			return (EObject) createPartStack();
		case BasicPackageImpl.PART_SASH_CONTAINER:
			return (EObject) createPartSashContainer();
		case BasicPackageImpl.WINDOW:
			return (EObject) createWindow();
		case BasicPackageImpl.TRIMMED_WINDOW:
			return (EObject) createTrimmedWindow();
		case BasicPackageImpl.TRIM_BAR:
			return (EObject) createTrimBar();
		case BasicPackageImpl.DIALOG:
			return (EObject) createDialog();
		case BasicPackageImpl.WIZARD_DIALOG:
			return (EObject) createWizardDialog();
