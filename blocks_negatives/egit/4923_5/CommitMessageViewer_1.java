				final int o;
				try {
					o = t.getOffsetAtLocation(new Point(e.x, e.y));
				} catch (IllegalArgumentException err) {
					return;
				}

				final StyleRange r = t.getStyleRangeAtOffset(o);
