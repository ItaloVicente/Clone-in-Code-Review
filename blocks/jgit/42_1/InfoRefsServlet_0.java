	private static Set<String> wantCapabilities(final HttpServletRequest req) {
		final String[] requestedCapabilityList = req.getParameterValues("want");
		if (requestedCapabilityList == null)
			return Collections.emptySet();
		final HashSet<String> want = new HashSet<String>();
		for (String singleWant : requestedCapabilityList) {
			for (String token : singleWant.split(" "))
				want.add(token);
		}
		return want;
	}

