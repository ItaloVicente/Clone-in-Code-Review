		private void diff(Edit r, long[] pCommon, int pIdx, int pEnd) {
			switch (r.getType()) {
			case INSERT:
			case DELETE:
				edits.add(r);
				return;

			case REPLACE:
				break;

			case EMPTY:
			default:
				throw new IllegalStateException();
			}

