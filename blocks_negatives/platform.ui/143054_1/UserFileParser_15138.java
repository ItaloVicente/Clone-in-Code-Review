    /**
     * Return the fabricated result for this example.
     *
     */
    private static IAdaptable getFabricatedResult() {
        GroupElement root = new GroupElement(
                MessageUtil.getString("Everybody"), null); //$NON-NLS-1$
        GroupElement userGroup = root.createSubGroup(MessageUtil
        GroupElement ottGroup = userGroup.createSubGroup(MessageUtil
        GroupElement uiTeam = ottGroup.createSubGroup(MessageUtil
        user1.setEmailAddress(new EmailAddress(MessageUtil
        user1
                .setAddress(new Address(
                        new StreetAddress(232, MessageUtil
                                .getString("Champlain")), MessageUtil.getString("Hull"), Integer.valueOf(5), MessageUtil.getString("A1B2C3"))); //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
        user1.setBirthday(new Birthday(18, 1, 1981));
        user1.setCoop(Boolean.TRUE);
        user1.setHairColor(new RGB(0, 0, 0));
        user1.setEyeColor(new RGB(0, 0, 0));
        user2.setEmailAddress(new EmailAddress(MessageUtil
        user2
                .setAddress(new Address(
                        new StreetAddress(),
                        MessageUtil.getString("Toronto"), Integer.valueOf(4), MessageUtil.getString("A1B2C3"))); //$NON-NLS-2$ //$NON-NLS-1$
        user2.setBirthday(new Birthday(7, 5, 1978));
        user2.setCoop(Boolean.TRUE);
        user2.setHairColor(new RGB(0, 0, 0));
        user2.setEyeColor(new RGB(0, 0, 0));
