		if (P_ID_FIRSTNAME.equals(propName)) {
			setFirstName((String) val);
			return;
		}
		if (P_ID_LASTNAME.equals(propName)) {
			setLastName((String) val);
			return;
		}
		if (P_ID_MIDDLENAME.equals(propName)) {
			setInitial((String) val);
			return;
		}
	}

	@Override
