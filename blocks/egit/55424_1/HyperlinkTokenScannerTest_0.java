			} else {
				Object data = token.getData();
				if (data instanceof TextAttribute) {
					int style = ((TextAttribute) data).getStyle();
					if ((style & TextAttribute.UNDERLINE) != 0) {
						ch = 'H';
					}
				}
