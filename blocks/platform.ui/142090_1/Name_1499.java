		StringBuilder outStringBuilder = new StringBuilder();
		if (getFirstName() != FIRSTNAME_DEFAULT) {
			outStringBuilder.append(getFirstName());
			outStringBuilder.append(" "); //$NON-NLS-1$
		}
		if (getInitial() != MIDDLENAME_DEFAULT) {
			outStringBuilder.append(getInitial());
			outStringBuilder.append(" "); //$NON-NLS-1$
		}
		if (getLastName() != LASTNAME_DEFAULT) {
			outStringBuilder.append(getLastName());
		}

		return outStringBuilder.toString();
	}
