			throws IOException
		for (Pack pack : getPacks()) {
			try {
				pack.copyPackAsIs(out
			} catch (IOException e ) {
				throw new StoredPackRepresentationNotAvailableException(pack
				 e);
			}
		}
