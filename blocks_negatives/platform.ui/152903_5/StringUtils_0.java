	/**
	 * Split String <b>line</b> with delimiter <b>delim</b> and return result
	 * inti array of String.
	 *
	 * @param line
	 * @param delim
	 * @return
	 */
	public static String[] split(String line, String delim) {
		List<String> list = new ArrayList<>();
		for (StringTokenizer t = new StringTokenizer(line, delim); t.hasMoreTokens(); list.add(t.nextToken())) {
			;
		}
		return list.toArray(new String[list.size()]);
	}

