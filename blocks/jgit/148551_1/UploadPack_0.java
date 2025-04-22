		boolean advertiseRefInWant = transferConfig.isAllowRefInWant()
				&& db.getConfig().getBoolean("uploadpack"
						"advertiserefinwant"
		caps.add(COMMAND_FETCH + '='
				+ (transferConfig.isAllowFilter() ? OPTION_FILTER + ' ' : "")
				+ (advertiseRefInWant ? CAPABILITY_REF_IN_WANT + ' ' : "")
				+ (transferConfig.isAllowSidebandAll()
						? OPTION_SIDEBAND_ALL + ' '
						: "")
				+ (cachedPackUriProvider != null ? "packfile-uris " : "")
				+ OPTION_SHALLOW);
