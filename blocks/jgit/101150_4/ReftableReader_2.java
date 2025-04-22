	public static ReftableReader emptyTable() {
		return new ReftableReader(BlockSource.from(EmptyTableHolder.I));
	}

	private static class EmptyTableHolder {
		final static byte[] I = makeEmptyTable();
		private static byte[] makeEmptyTable() {
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				new ReftableWriter().begin(out).finish();
				return out.toByteArray();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

