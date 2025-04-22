		for (Pack pack : getPacks()) {
			try {
				pack.copyPackAsIs(out
			} catch (IOException ioe) {
				if (FileUtils.isStaleFileHandleInCausalChain(ioe)) {
					throw new PackNotFoundException(pack
				}
				throw ioe;
			}
		}
