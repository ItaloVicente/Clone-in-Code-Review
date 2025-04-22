		for (String uriString : uriStrings) {
			sb.append(uriString);
			lb.append(uriString.length());
			lb.append(" "); //$NON-NLS-1$
		}
		prefs.put(USED_URIS_PREF, sb.toString());
		prefs.put(USED_URIS_LENGTH_PREF, lb.toString());
