		UserElement user3 = uiTeam.createUser("arnold"); //$NON-NLS-1$
		user3.setFullName(new Name(MessageUtil.getString("Arnold_Palmer"))); //$NON-NLS-1$
		user3.setEmailAddress(new EmailAddress(MessageUtil
				.getString("apalmer@company.com"))); //$NON-NLS-1$
		user3.setPhoneNumber("x567"); //$NON-NLS-1$
		user3
				.setAddress(new Address(
						new StreetAddress(),
						MessageUtil.getString("Ottawa"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
		user3.setBirthday(new Birthday(11, 23, 1962));
		user3.setHairColor(new RGB(0, 0, 0));
		user3.setEyeColor(new RGB(0, 0, 0));
