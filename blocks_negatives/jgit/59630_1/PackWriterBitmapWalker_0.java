			return new BitmapRevFilter() {
				protected boolean load(RevCommit cmit) {
					if (seen.contains(cmit))
						return false;
					return bitmapResult.add(cmit, Constants.OBJ_COMMIT);
				}
			};
