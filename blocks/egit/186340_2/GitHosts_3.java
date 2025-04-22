	public static void loadFromPreferences(String preferenceData,
			BiConsumer<String, String> consumer) {
		if (StringUtils.isEmptyOrNull(preferenceData)) {
			return;
		}
		String[] lines = preferenceData.split("\n"); //$NON-NLS-1$
		for (String line : lines) {
			if (StringUtils.isEmptyOrNull(line)) {
				continue;
			}
			String[] parts = line.split("\t", 2); //$NON-NLS-1$
			if (parts.length != 2) {
				continue;
			}
			try {
				GitHosts.ServerType.valueOf(parts[0]);
				String hostPattern = parts[1];
				if (StringUtils.isEmptyOrNull(hostPattern)) {
					continue;
				}
				Pattern.compile(hostPattern);
				consumer.accept(parts[0], hostPattern);
			} catch (IllegalArgumentException e) {
				Activator.logError(MessageFormat.format(
						CoreText.GitHosts_invalidPreference,
						GitCorePreferences.core_gitServers, line), e);
			}
		}
