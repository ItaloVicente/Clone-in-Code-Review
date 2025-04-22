	private void assertIcon(CommandContributionItem cmd, String targetIcon) throws IllegalArgumentException, IllegalAccessException {
		ImageDescriptor icon = (ImageDescriptor) iconField.get(cmd);
		assertNotNull(icon);
		String iconString = icon.toString();
		assertEquals(targetIcon+')', iconString.substring(iconString
				.lastIndexOf('/')));
	}
	private void assertIcon(HandledContributionItem item, String targetIcon) {
		final MHandledItem model = item.getModel();
		String iconString = model.getIconURI();
		assertEquals(targetIcon, iconString.substring(iconString
				.lastIndexOf('/')));
	}
