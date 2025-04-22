				while (entry == null && it.hasNext()) {
					StoredBitmap sb = it.next();
					if (newPackIndex.findPosition(sb) != -1)
						entry = new Entry(sb
				}
				return entry != null;
