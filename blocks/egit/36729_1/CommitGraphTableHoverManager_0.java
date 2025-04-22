	private Information computeInformationForName(PersonIdent ident,
			ViewerCell cell) {
		String nameWithEmail = ident.getName()
				+ " <" + ident.getEmailAddress() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
		return new Information(nameWithEmail, cell.getBounds());
	}

