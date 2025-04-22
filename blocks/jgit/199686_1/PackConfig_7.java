		String[] excludedRefsPrefixesArray = rc.getStringList(CONFIG_PACK_SECTION
			null
			CONFIG_KEY_BITMAP_EXCLUDED_REFS_PREFIXES);
		if(excludedRefsPrefixesArray.length > 0) {
			setBitmapExcludedRefsPrefixes(excludedRefsPrefixesArray);
		}
