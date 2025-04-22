	private static IAdaptable getFabricatedResult() {
		GroupElement root = new GroupElement(
				MessageUtil.getString("Everybody"), null); //$NON-NLS-1$
		GroupElement userGroup = root.createSubGroup(MessageUtil
				.getString("Company_Inc")); //$NON-NLS-1$
		GroupElement ottGroup = userGroup.createSubGroup(MessageUtil
				.getString("Waterloo_Lab")); //$NON-NLS-1$
		userGroup.createSubGroup(MessageUtil.getString("Toronto_Lab")); //$NON-NLS-1$
		userGroup.createSubGroup(MessageUtil.getString("Hamilton_Lab")); //$NON-NLS-1$
		userGroup.createSubGroup(MessageUtil.getString("London_Lab")); //$NON-NLS-1$
		userGroup.createSubGroup(MessageUtil.getString("Grimsby_Lab")); //$NON-NLS-1$
		GroupElement uiTeam = ottGroup.createSubGroup(MessageUtil
				.getString("Team1")); //$NON-NLS-1$
		UserElement user1 = uiTeam.createUser("richard"); //$NON-NLS-1$
		user1.setFullName(new Name(MessageUtil.getString("Richard_Zokol"))); //$NON-NLS-1$
		user1.setEmailAddress(new EmailAddress(MessageUtil
				.getString("rzokol@company.com"))); //$NON-NLS-1$
		user1.setPhoneNumber("x789"); //$NON-NLS-1$
		user1
				.setAddress(new Address(
						new StreetAddress(232, MessageUtil
								.getString("Champlain")), MessageUtil.getString("Hull"), Integer.valueOf(5), MessageUtil.getString("A1B2C3"))); //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
		user1.setBirthday(new Birthday(18, 1, 1981));
		user1.setCoop(Boolean.TRUE);
		user1.setHairColor(new RGB(0, 0, 0));
		user1.setEyeColor(new RGB(0, 0, 0));
		UserElement user2 = uiTeam.createUser("george"); //$NON-NLS-1$
		user2.setFullName(new Name(MessageUtil.getString("George_Knudson"))); //$NON-NLS-1$
		user2.setEmailAddress(new EmailAddress(MessageUtil
				.getString("gknudson@company.com"))); //$NON-NLS-1$
		user2.setPhoneNumber("x678"); //$NON-NLS-1$
		user2
				.setAddress(new Address(
						new StreetAddress(),
						MessageUtil.getString("Toronto"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
		user2.setBirthday(new Birthday(7, 5, 1978));
		user2.setCoop(Boolean.TRUE);
		user2.setHairColor(new RGB(0, 0, 0));
		user2.setEyeColor(new RGB(0, 0, 0));
