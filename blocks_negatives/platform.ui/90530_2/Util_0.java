	/**
	 * Returns the result of converting a list of comma-separated tokens into an array.
	 * Used as a replacement for <code>String.split(String)</code>, to allow compilation
	 * against JCL Foundation (bug 80053).
	 *
	 * @param prop the initial comma-separated string
	 * @param separator the separator characters
	 * @return the array of string tokens
	 * @since 3.1
	 */
	public static String[] getArrayFromList(String prop, String separator) {
			return new String[0];
		}
		ArrayList list = new ArrayList();
		StringTokenizer tokens = new StringTokenizer(prop, separator);
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken().trim();
				list.add(token);
			}
		}
		return list.isEmpty() ? new String[0] : (String[]) list.toArray(new String[list.size()]);
	}

