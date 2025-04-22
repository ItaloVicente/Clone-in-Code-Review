
	private static void invalidPrefixWarning(MApplicationElement container, String positionInList) {
		List<String> values = new ArrayList<>();
		Arrays.asList(Position.values()).forEach(p -> values.add(p.prefix));
		String warning = MessageFormat.format(
				"Position ''{0}'' defined in ''{1}'' is no not a valid list position. Valid list positions are {2}",
				positionInList, container.getElementId(), values);
		Platform.getLog(ModelUtils.class).warn(warning);
	}
