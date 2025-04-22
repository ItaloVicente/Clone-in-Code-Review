			if (seen == null) {
				walker.setRevFilter(new AddToBitmapFilter(bitmapResult));
			} else {
				walker.setRevFilter(
						new AddUnseenToBitmapFilter(seen
			}
