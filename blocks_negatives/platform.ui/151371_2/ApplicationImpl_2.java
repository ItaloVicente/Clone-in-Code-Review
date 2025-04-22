	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @since 1.1
	 * @deprecated
	 * @noreference See {@link org.eclipse.e4.ui.model.application.MApplication#getDialogs() model documentation} for details.
	 * @generated
	 */
	@Deprecated
	@Override
	public List<MDialog> getDialogs() {
		if (dialogs == null) {
			dialogs = new EObjectResolvingEList<MDialog>(MDialog.class, this,
					ApplicationPackageImpl.APPLICATION__DIALOGS);
		}
		return dialogs;
	}

