			packedRefsBytes = refsBytes;
		}

		private boolean hasTheSamePackedRefsBytes(byte[] cmpPackedRefsBytes) {
			return packedRefsBytes != null
				&& cmpPackedRefsBytes != null
				&& Arrays.equals(
				packedRefsBytes
