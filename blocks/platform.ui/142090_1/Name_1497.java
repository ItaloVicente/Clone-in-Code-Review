		if (P_ID_FIRSTNAME.equals(property)) {
			setFirstName(FIRSTNAME_DEFAULT);
			return;
		}
		if (P_ID_LASTNAME.equals(property)) {
			setLastName(LASTNAME_DEFAULT);
			return;
		}
		if (P_ID_MIDDLENAME.equals(property)) {
			setInitial(MIDDLENAME_DEFAULT);
			return;
		}
	}

	private void setFirstName(String newFirstName) {
		firstName = newFirstName;
	}

	private void setInitial(String newInitial) {
		initial = newInitial;
	}

	private void setLastName(String newLastName) {
		lastName = newLastName;
	}

	@Override
