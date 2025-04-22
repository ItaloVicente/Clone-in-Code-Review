		int dashg = revstr.indexOf("-g");
		if (4 < revstr.length() && 0 <= dashg
				&& isHex(revstr.charAt(dashg + 2))
				&& isHex(revstr.charAt(dashg + 3))
				&& isAllHex(revstr
			String s = revstr.substring(dashg + 2);
			if (AbbreviatedObjectId.isId(s))
				return resolveAbbreviation(s);
		}

