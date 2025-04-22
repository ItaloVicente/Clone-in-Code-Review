	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @since 1.1
	 * @deprecated
	 * @noreference See {@link MDialog model documentation} for details.
	 * @generated  NOT
	 */
	@Deprecated
	@Override
	public MDialog createDialog() {
		DialogImpl dialog = new DialogImpl();
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		Status s = new Status(IStatus.ERROR, bundle.getSymbolicName(), // $NON-NLS-1$
		Platform.getLog(bundle).log(s);
		return dialog;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @since 1.1
	 * @deprecated
	 * @noreference See {@link MWizardDialog model documentation} for details.
	 * @generated NOT
	 */
	@Deprecated
	@Override
	public MWizardDialog createWizardDialog() {
		WizardDialogImpl wizardDialog = new WizardDialogImpl();
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		Status s = new Status(IStatus.ERROR, bundle.getSymbolicName(), // $NON-NLS-1$
		Platform.getLog(bundle).log(s);
		return wizardDialog;
	}

