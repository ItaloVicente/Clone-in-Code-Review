		UserElement user4 = uiTeam.createUser("lee"); //$NON-NLS-1$
		user4.setFullName(new Name(MessageUtil.getString("Lee_Trevino"))); //$NON-NLS-1$
		user4.setEmailAddress(new EmailAddress(MessageUtil
				.getString("ltrevino@company.com"))); //$NON-NLS-1$
		user4.setPhoneNumber("x456"); //$NON-NLS-1$
		user4
				.setAddress(new Address(
						new StreetAddress(),
						MessageUtil.getString("Ottawa"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
		UserElement user5 = uiTeam.createUser("tiger"); //$NON-NLS-1$
		user5.setFullName(new Name(MessageUtil.getString("Tiger_Woods"))); //$NON-NLS-1$
		user5.setEmailAddress(new EmailAddress(MessageUtil
				.getString("twoods@company.com"))); //$NON-NLS-1$
		user5.setPhoneNumber("x345"); //$NON-NLS-1$
		user5
				.setAddress(new Address(
						new StreetAddress(),
						MessageUtil.getString("Ottawa"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
		UserElement user6 = uiTeam.createUser("jack"); //$NON-NLS-1$
		user6.setFullName(new Name(MessageUtil.getString("Jack_Nicklaus"))); //$NON-NLS-1$
		user6.setEmailAddress(new EmailAddress(MessageUtil
				.getString("jnicklaus@company.com"))); //$NON-NLS-1$
		user6.setPhoneNumber("x234 "); //$NON-NLS-1$
		user6
				.setAddress(new Address(
						new StreetAddress(),
						MessageUtil.getString("Ottawa"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
		UserElement greg = uiTeam.createUser("weslock"); //$NON-NLS-1$
		greg.setFullName(new Name(MessageUtil.getString("Weslock"))); //$NON-NLS-1$
		greg.setEmailAddress(new EmailAddress(MessageUtil
				.getString("weslock@company.com"))); //$NON-NLS-1$
		greg.setPhoneNumber("x123"); //$NON-NLS-1$
		greg
				.setAddress(new Address(
						new StreetAddress(),
						MessageUtil.getString("Ottawa"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
