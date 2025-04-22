
	private PushMode getDefaultPushMode() {
		return repo.getConfig().getEnum(PushMode.values()
				ConfigConstants.CONFIG_SECTION_PUSH
				ConfigConstants.CONFIG_KEY_DEFAULT
	}
