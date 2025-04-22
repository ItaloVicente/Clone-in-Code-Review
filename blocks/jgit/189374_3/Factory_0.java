			if (StringUtils.isEmptyOrNull(identityAgent)) {
				return new PageantConnector();
			}
			String winPath = identityAgent.replace('/'
			if (PageantConnector.DESCRIPTOR.getIdentityAgent()
					.equalsIgnoreCase(winPath)) {
				return new PageantConnector();
			}
				return new WinPipeConnector(winPath);
			}
			throw new IOException(MessageFormat.format(
					Texts.get().errUnknownIdentityAgent
