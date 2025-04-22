				GC gc = new GC(ExpandableComposite.this);
				gc.setFont(getFont());
				FontMetrics fm = gc.getFontMetrics();
				int textHeight = fm.getHeight();
				gc.dispose();
				if (textClient != null
						&& (expansionStyle & LEFT_TEXT_CLIENT_ALIGNMENT) != 0) {
					textHeight = Math.max(textHeight, tcsize.y);
				}
				int ty = textHeight / 2 - tsize.y / 2 + 1;
