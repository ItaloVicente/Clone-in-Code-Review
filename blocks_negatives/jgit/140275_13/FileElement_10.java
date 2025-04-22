	/**
	 * @param input
	 *            the input string
	 * @return the replaced input string
	 * @throws IOException
	 */
	public String replaceVariable(String input) throws IOException {
		return input.replace("$" + type.name(), getFile().getPath()); //$NON-NLS-1$
	}

	/**
	 * @param env
	 *            the environment where this element should be added
	 * @throws IOException
	 */
	public void addToEnv(Map<String, String> env) throws IOException {
		env.put(type.name(), getFile().getPath());
	}

