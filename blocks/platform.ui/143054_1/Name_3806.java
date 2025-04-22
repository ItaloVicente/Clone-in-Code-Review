		if (key.equals(P_ID_FIRSTNAME))
			return getFirstName() != FIRSTNAME_DEFAULT;
		if (key.equals(P_ID_LASTNAME))
			return getLastName() != LASTNAME_DEFAULT;
		if (key.equals(P_ID_MIDDLENAME))
			return getInitial() != MIDDLENAME_DEFAULT;
		return false;
	}

	@Override
