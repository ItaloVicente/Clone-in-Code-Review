			} else if (widget instanceof Link) {
				Link link = (Link) widget;
				if ("link".equals(pseudo)) {
					link.setLinkForeground(newColor);
				} else {
					CSSSWTColorHelper.setForeground(link, newColor);
				}
