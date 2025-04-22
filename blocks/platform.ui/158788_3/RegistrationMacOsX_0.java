	private String determineHandlerLocation(String lsRegisterOutput, String scheme) throws Exception {

		String[] lsRegisterEntries = lsRegisterOutput.split("-{80}\n"); //$NON-NLS-1$
		String startOfFirstLine;
		String prefixOfSchemeList;

		if (Stream.of(lsRegisterEntries).parallel().anyMatch(s -> s.startsWith("BundleClass:"))) { //$NON-NLS-1$
			startOfFirstLine = "BundleClass"; //$NON-NLS-1$
			prefixOfSchemeList = "bindings:"; //$NON-NLS-1$
		} else {
			startOfFirstLine = "bundle id"; //$NON-NLS-1$
			prefixOfSchemeList = "claimed schemes:"; //$NON-NLS-1$

		}
