		copyProperty(
				hostConfig.getProperty(SshConstants.PREFERRED_AUTHENTICATIONS,
						getAttribute(PREFERRED_AUTHENTICATIONS)),
				PREFERRED_AUTHS);
		setAttribute(HOST_CONFIG_ENTRY, hostConfig);
		setAttribute(ORIGINAL_REMOTE_ADDRESS, address);
