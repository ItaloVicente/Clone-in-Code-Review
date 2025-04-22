		caps.add(COMMAND_FETCH + '='
				+ (transferConfig.isAllowFilter() ? OPTION_FILTER + ' ' : "")
				+ (advertiseRefInWant ? CAPABILITY_REF_IN_WANT + ' ' : "")
				+ (transferConfig.isAdvertiseSidebandAll()
						? OPTION_SIDEBAND_ALL + ' '
						: "")
				+ (cachedPackUriProvider != null ? "packfile-uris " : "")
				+ OPTION_SHALLOW);
