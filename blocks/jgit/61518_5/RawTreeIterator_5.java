
	public static class Subtree extends RawTreeIterator {
		private final RawTreeIterator parent;

		Subtree(RawTreeIterator parent
			super(raw);
			this.parent = parent;
		}

		@Override
		public final RawTreeIterator exit() {
			return parent;
		}

		public final RawTreeIterator parent() {
			return parent;
		}

		@Override
		public final int getPathLength() {
			int len = getNameLength();
			for (RawTreeIterator p = parent;;) {
				len += p.getNameLength();
				if (p instanceof Subtree) {
					p = ((Subtree) p).parent;
				} else {
					return len;
				}
			}
		}

		@Override
		public final void copyPath(byte[] out
			int outPtr = outPos + pathLen;
			for (RawTreeIterator p = this;;) {
				int nameLen = p.getNameLength();
				int o = outPtr - nameLen;
				System.arraycopy(p.raw
				outPtr = o;
				if (p instanceof Subtree) {
					out[--outPtr] = '/';
					p = ((Subtree) p).parent;
				} else {
					break;
				}
			}
		}

		@Override
		public String toString() {
			byte[] path = new byte[getPathLength()];
			copyPath(path
			return String.format("RawTreeIterator[%o %s %s]"
					Integer.valueOf(mode)
		}
	}
