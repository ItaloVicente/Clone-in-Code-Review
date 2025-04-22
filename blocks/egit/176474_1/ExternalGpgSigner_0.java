	private void checkTerminalPrompt(String gpgTraceLine) {
		String[] parts = gpgTraceLine.split(" "); //$NON-NLS-1$
		if (parts.length > 3 && "[GNUPG:]".equals(parts[0]) //$NON-NLS-1$
				&& "PINENTRY_LAUNCHED".equals(parts[1])) { //$NON-NLS-1$
			String pinentryType = parts[3];
			if ("tty".equals(pinentryType) || "curses".equals(pinentryType)) { //$NON-NLS-1$ //$NON-NLS-2$
				throw new GpgConfigurationException(MessageFormat.format(
						CoreText.ExternalGpgSigner_ttyInput, gpgTraceLine));
			}
		}
	}

