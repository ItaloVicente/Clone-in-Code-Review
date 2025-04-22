		if (property.equals(P_ID_ADDRESS)) {
			setAddress(address_Default);
			return;
		}
		if (property.equals(P_ID_FULLNAME)) {
			setFullName(fullName_Default);
			return;
		}
		if (property.equals(P_ID_PHONENUMBER)) {
			setPhoneNumber(phoneNumber_Default);
			return;
		}
		if (property.equals(P_ID_EMAIL)) {
			setEmailAddress(emailAddress_Default);
			return;
		}
		if (property.equals(P_ID_COOP)) {
			setCoop(coop_Default);
		}
		if (property.equals(P_ID_BDAY)) {
			setBirthday(birthday_Default);
			return;
		}
		if (property.equals(P_ID_SALARY)) {
			setSalary(salary_Default);
			return;
		}
		if (property.equals(P_ID_HAIRCOLOR)) {
			setHairColor(hairColor_Default);
			return;
		}
		if (property.equals(P_ID_EYECOLOR)) {
			setEyeColor(eyeColor_Default);
			return;
		}
		super.resetPropertyValue(property);
	}

	public void setAddress(Address newAddress) {
		address = newAddress;
	}

	public void setBirthday(Birthday newBirthday) {
		birthday = new Birthday();
	}

	public void setCoop(Boolean newCoop) {
		coop = newCoop;
	}

	public void setEmailAddress(EmailAddress newEmailAddress) {
		emailAddress = newEmailAddress;
	}

	public void setEyeColor(RGB newEyeColor) {
		eyeColor = newEyeColor;
	}

	public void setFullName(Name newFullName) {
		fullName = newFullName;
	}

	public void setHairColor(RGB newHairColor) {
		hairColor = newHairColor;
	}

	public void setPhoneNumber(String newPhoneNumber) {
		phoneNumber = newPhoneNumber;
	}

	@Override
