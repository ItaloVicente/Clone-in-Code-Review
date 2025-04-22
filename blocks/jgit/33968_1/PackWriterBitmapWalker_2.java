			try {
				if (!bitmapResult.contains(obj)) {
					walker.markStart(walker.parseAny(obj));
					marked = true;
				}
			} catch (MissingObjectException e) {
				if (ignoreMissingStart)
					continue;
				throw e;
