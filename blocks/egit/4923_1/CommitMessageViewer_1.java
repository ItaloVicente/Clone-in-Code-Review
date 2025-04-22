				final StyleRange r;
				try {
					r = t.getStyleRangeAtOffset(o);
				} catch (IllegalArgumentException err) {
					return;
				}
