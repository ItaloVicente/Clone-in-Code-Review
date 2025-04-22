	/**
	 * The method shows a change credentials dialog for a given URI. The user
	 * field is taken from the URI if a user is present in the URI. In this case
	 * the user is not editable.
	 *
	 * @param parent
	 * @param uri
	 * @return credentials, <code>null</code> if the user canceled the dialog.
	 */
	public static UserPasswordCredentials changeCredentials(Shell parent,
			URIish uri) {
		LoginDialog dialog = new LoginDialog(parent, uri);
		dialog.changeCredentials = true;
		if (dialog.open() == OK)
			return dialog.credentials;
		return null;
