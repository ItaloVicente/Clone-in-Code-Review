	private Set<String> getCapabilitiesV2() throws TransportException {
		Set<String> capabilities = new LinkedHashSet<>();
		if (noProgress) {
			capabilities.add(OPTION_NO_PROGRESS);
		}
		if (includeTags) {
			capabilities.add(OPTION_INCLUDE_TAG);
		}
		if (allowOfsDelta) {
			capabilities.add(OPTION_OFS_DELTA);
		}
		if (thinPack) {
			capabilities.add(OPTION_THIN_PACK);
		}
		if (!filterSpec.isNoOp()
				&& !fetchCapabilities.contains(OPTION_FILTER)) {
			throw new PackProtocolException(uri,
					JGitText.get().filterRequiresCapability);
		}
		return capabilities;
	}

