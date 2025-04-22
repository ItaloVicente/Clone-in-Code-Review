		if (requestPolicy != null)
			up.setRequestPolicy(requestPolicy);
		if (refFilter != null)
			up.setRefFilter(refFilter);
		up.setExtraParameters(Sets.of("version=2"));
		if (hook != null) {
			up.setProtocolV2Hook(hook);
