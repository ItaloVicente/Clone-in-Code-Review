	public static void configureTLS(SSLSocket socket) {
		Set<String> enabled = new LinkedHashSet<>(
				Arrays.asList(socket.getEnabledProtocols()));
		for (String s : socket.getSupportedProtocols()) {
				enabled.add(s);
			}
		}
		Set<String> configured = getConfiguredProtocols();
		if (!configured.isEmpty()) {
			enabled.retainAll(configured);
		}
		if (!enabled.isEmpty()) {
			socket.setEnabledProtocols(enabled.toArray(new String[0]));
		}
	}

	private static Set<String> getConfiguredProtocols() {
		Set<String> result = configuredHttpsProtocols;
		if (result == null) {
			if (StringUtils.isEmptyOrNull(configured)) {
				result = Collections.emptySet();
			} else {
				result = new LinkedHashSet<>(
						Arrays.asList(configured.split("\\s*
			}
			configuredHttpsProtocols = result;
		}
		return result;
	}

	private static String getProperty(String property) {
		try {
			return SystemReader.getInstance().getProperty(property);
		} catch (SecurityException e) {
			return null;
		}
	}

