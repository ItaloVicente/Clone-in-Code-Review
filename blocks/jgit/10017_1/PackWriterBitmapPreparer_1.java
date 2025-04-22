		this.commitBitmapIndex = new BitmapIndexImpl(writeBitmaps);
		BitmapIndex prevBitmapIndex = reader.getBitmapIndex();
		if (prevBitmapIndex != null)
			this.bitmapIndex = new BitmapIndexImpl(
					new PackBitmapIndexRemapper(prevBitmapIndex
		else
			this.bitmapIndex = this.commitBitmapIndex;
