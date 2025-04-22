			CTabItem item = (CTabItem) widget;
			if (item.getFont() != font) {
				CSSSWTFontHelper.storeDefaultFont(item);
				item.setFont(font);
			}
		} else if (widget instanceof CTabFolder) {
			CTabFolder folder = (CTabFolder) widget;
