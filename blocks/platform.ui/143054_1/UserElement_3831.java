		return this.toString();
	}

	EmailAddress getEmailAddress() {
		if (emailAddress == null)
			emailAddress = new EmailAddress();
		return emailAddress;
	}

	private RGB getEyeColor() {
		if (eyeColor == null)
			eyeColor = new RGB(60, 60, 60);
		return eyeColor;
	}

	private Name getFullName() {
		if (fullName == null)
			fullName = new Name(getName());
		return fullName;
	}

	private RGB getHairColor() {
		if (hairColor == null)
			hairColor = new RGB(255, 255, 255);
		return hairColor;
	}

	private String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
