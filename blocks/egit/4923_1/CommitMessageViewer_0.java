				final StyleRange r;
				try {
					r = t.getStyleRangeAtOffset(o);
				} catch (IllegalArgumentException err) {
					t.setCursor(sys_normalCursor);
					return;
				}
