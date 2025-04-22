
	private boolean shouldDisplayAfterSuppressDuplicates(IMarker marker, Map  supppressDuplicatesMap) {
		try {
			Integer lineNumber = null;
			try {
				lineNumber = Integer.valueOf(marker.getAttribute(IMarker.LINE_NUMBER).toString() );
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			File file = new File(marker.getResource().getLocation().toString());
			String canonicalPath = file.getCanonicalPath().toString();
			String message = marker.getAttribute(IMarker.MESSAGE).toString();
			if (lineNumber != null) {
				if ( supppressDuplicatesMap.containsKey(canonicalPath)) {
					 HashMap map2 = (HashMap)supppressDuplicatesMap.get(canonicalPath);
					 if (map2.containsKey(lineNumber+message)) {
					 }
					 else {
						 map2.put( lineNumber+message , marker);
						 return true;
					 }
				}
				else {
					Map map2 = new HashMap();
					map2.put( lineNumber+message , marker);
					supppressDuplicatesMap.put(canonicalPath, map2);
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
