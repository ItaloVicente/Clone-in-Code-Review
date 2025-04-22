
	public void println() {
		EWAHCompressedBitmap bitmapTags = getTags();
		IntIterator intIterator = bitmapTags.intIterator();
		while(intIterator.hasNext()) {
			System.out.println("BitmapTag(" + intIterator.next() + ")");
		}
	}
