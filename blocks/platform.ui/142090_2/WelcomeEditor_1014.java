				final String ellipsis = "..."; //$NON-NLS-1$
				int ellipseWidth = gc.textExtent(ellipsis, SWT.DRAW_MNEMONIC).x;
				int length = text.length();
				int end = length - 1;
				while (end > 0) {
					text = text.substring(0, end);
					int l1 = gc.textExtent(text, SWT.DRAW_MNEMONIC).x;
					if (l1 + ellipseWidth <= width) {
						return text + ellipsis;
					}
					end--;
				}
				return text + ellipsis;
			}
