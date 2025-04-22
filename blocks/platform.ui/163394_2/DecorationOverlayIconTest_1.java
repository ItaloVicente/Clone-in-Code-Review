
	private static class SimpleImageDescriptor extends ImageDescriptor {
		private String pretendFileName;

		public SimpleImageDescriptor(String pretendFileName) {
			this.pretendFileName = pretendFileName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((pretendFileName == null) ? 0 : pretendFileName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			SimpleImageDescriptor other = (SimpleImageDescriptor) obj;
			if (pretendFileName == null) {
				if (other.pretendFileName != null) {
					return false;
				}
			} else if (!pretendFileName.equals(other.pretendFileName)) {
				return false;
			}
			return true;
		}

	}

	public void testEqualsAndHashCode2() {

		SimpleImageDescriptor equalButDifferent1 = new SimpleImageDescriptor("pretend_file_name");
		SimpleImageDescriptor equalButDifferent2 = new SimpleImageDescriptor("pretend_file_name");
		assertTrue(equalButDifferent1.equals(equalButDifferent2));
		assertEquals(equalButDifferent1.hashCode(), equalButDifferent2.hashCode());

		DecorationOverlayIcon equalButDifferentIcon1 = new DecorationOverlayIcon(equalButDifferent1, overlayDescriptor1,
				IDecoration.TOP_LEFT);
		DecorationOverlayIcon equalButDifferentIcon2 = new DecorationOverlayIcon(equalButDifferent2, overlayDescriptor1,
				IDecoration.TOP_LEFT);
		assertTrue(equalButDifferentIcon1.equals(equalButDifferentIcon2));
		assertEquals(equalButDifferentIcon1.hashCode(), equalButDifferentIcon2.hashCode());
	}
