		if (data instanceof String) {
			for (StringTokenizer toker = new StringTokenizer((String) data, ","); toker.hasMoreTokens(); ) {
				String token = toker.nextToken();
				if (token.equals(PARAM_OUTLINE))
					useOutline = true;
			}
