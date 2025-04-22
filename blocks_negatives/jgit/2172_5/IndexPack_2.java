		resolveChildDeltaChain(type, data, a);
		resolveChildDeltaChain(type, data, b);
	}

	private void resolveChildDeltaChain(final int type, final byte[] data,
			UnresolvedDelta a) throws IOException {
		while (a != null) {
			resolveDeltas(a.position, a.crc, type, data, null);
			a = a.next;
		}
