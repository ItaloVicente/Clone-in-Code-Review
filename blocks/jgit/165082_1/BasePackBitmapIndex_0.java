		EWAHCompressedBitmap getBitmapWithoutCaching() {
			Object r = bitmapContainer;
			if (r instanceof EWAHCompressedBitmap)
				return (EWAHCompressedBitmap) r;

			XorCompressedBitmap xb = (XorCompressedBitmap) r;
			EWAHCompressedBitmap out = xb.bitmap;
			for (;;) {
				r = xb.xorBitmap.bitmapContainer;
				if (r instanceof EWAHCompressedBitmap) {
					out = out.xor((EWAHCompressedBitmap) r);
					out.trim();
					return out;
				}
				xb = (XorCompressedBitmap) r;
				out = out.xor(xb.bitmap);
			}
		}

