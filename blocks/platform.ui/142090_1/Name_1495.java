		if (P_ID_FIRSTNAME.equals(propKey))
			return getFirstName();
		if (P_ID_LASTNAME.equals(propKey))
			return getLastName();
		if (P_ID_MIDDLENAME.equals(propKey))
			return getInitial();
		return null;
	}

	@Override
