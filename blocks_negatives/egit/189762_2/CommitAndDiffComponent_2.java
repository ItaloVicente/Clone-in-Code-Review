			Object defaults = defaultToken.getData();
			TextAttribute italic;
			if (defaults instanceof TextAttribute) {
				TextAttribute defaultAttribute = (TextAttribute) defaults;
				int style = defaultAttribute.getStyle() ^ SWT.ITALIC;
				italic = new TextAttribute(defaultAttribute.getForeground(),
						defaultAttribute.getBackground(), style,
						defaultAttribute.getFont());
			} else {
				italic = new TextAttribute(null, null, SWT.ITALIC);
			}
			italicToken = new Token(italic);
