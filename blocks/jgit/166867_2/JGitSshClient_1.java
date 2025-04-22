	private AttributeRepository chain(AttributeRepository self
			AttributeRepository parent) {
		if (self == null) {
			return Objects.requireNonNull(parent);
		}
		if (parent == null || parent == self) {
			return self;
		}
		return new ChainingAttributes(self
	}

	private AttributeRepository sessionAttributes(AttributeRepository parent
			HostConfigEntry hostConfig
		Map<AttributeKey<?>
		data.put(HOST_CONFIG_ENTRY
		data.put(ORIGINAL_REMOTE_ADDRESS
		String preferredAuths = hostConfig.getProperty(
				SshConstants.PREFERRED_AUTHENTICATIONS
				resolveAttribute(PREFERRED_AUTHENTICATIONS));
		if (!StringUtils.isEmptyOrNull(preferredAuths)) {
			data.put(SessionAttributes.PROPERTIES
					Collections.singletonMap(PREFERRED_AUTHS
