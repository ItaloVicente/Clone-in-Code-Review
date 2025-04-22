			try {
				if (!bitmapResult.contains(obj)) {
					walker.markStart(walker.parseAny(obj));
					marked = true;
				}

			} catch (MissingObjectException e) {
				if (ignoreMissingSeen
						&& (seen == null || seen.contains(e.getObjectId())))
					continue;
				throw e;
