    /**
     * Writes a symref (e.g. HEAD) to disk
     *
     * @param name symref name
     * @param target pointed to ref
     * @throws IOException
     */
    public void writeSymref(final String name, final String target)
			throws IOException {
		refs.link(name, target);
	}

