		String[] lsRegisterEntries = lsRegisterDump.split("-{80}\n"); //$NON-NLS-1$
		String keyOfFirstLine;
		String prefixOfSchemeList;

		boolean oldFormat = Stream.of(lsRegisterEntries).parallel().anyMatch(s -> s.startsWith("BundleClass:")); //$NON-NLS-1$
		if (oldFormat) {
			keyOfFirstLine = "BundleClass"; //$NON-NLS-1$
			prefixOfSchemeList = "bindings:"; //$NON-NLS-1$
		} else {
			keyOfFirstLine = "bundle id"; //$NON-NLS-1$
			prefixOfSchemeList = "claimed schemes:"; //$NON-NLS-1$

		}
