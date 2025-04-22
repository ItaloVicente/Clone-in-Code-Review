	private void resolveChildDeltas(final long pos, int type, byte[] data,
			PackedObjectInfo oe) throws IOException {
		UnresolvedDelta a = reverse(removeBaseById(oe));
		UnresolvedDelta b = reverse(baseByPos.remove(pos));
		while (a != null && b != null) {
			if (a.position < b.position) {
				resolveDeltas(a.position, a.crc, type, data, null);
				a = a.next;
			} else {
				resolveDeltas(b.position, b.crc, type, data, null);
				b = b.next;
			}
