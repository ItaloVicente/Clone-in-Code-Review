		Set<String> prefixes = new HashSet<>();
		for (Ref toGet : askFor.values()) {
			String src = toGet.getName();
			prefixes.add(src);
			prefixes.add(Constants.R_REFS + src);
			prefixes.add(Constants.R_HEADS + src);
			prefixes.add(Constants.R_TAGS + src);
		}
		conn = transport.openFetch(Collections.emptyList()
				prefixes.toArray(new String[0]));
