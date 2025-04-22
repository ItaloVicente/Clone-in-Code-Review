			} else if (widget instanceof Link) {
				Link link = (Link) widget;
				if ("link".equals(pseudo)) {
					System.err.println("_NOT_ setting link color to " + newColor);
				} else {
					CSSSWTColorHelper.setForeground(link, newColor);
				}
