			if (pack.exists()) {
				PackBitmapIndex index = pack.getBitmapIndex();
				if (needBitmap.removeAllOrNone(index))
					return Collections.<CachedPack> singletonList(
							new LocalCachedPack(Collections.singletonList(pack)));
			}
