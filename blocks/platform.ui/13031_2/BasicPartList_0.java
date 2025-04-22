		private Font boldFont;

		public BasicStackListLabelProvider() {
			Font font = Display.getDefault().getSystemFont();
			FontData[] fontDatas = font.getFontData();
			for (FontData fontData : fontDatas) {
				fontData.setStyle(fontData.getStyle() | SWT.BOLD);
			}
			boldFont = new Font(Display.getDefault(), fontDatas);
		}

		@Override
		public Font getFont(Object element) {
			if (element instanceof MPart) {
				MPart part = (MPart) element;
				CTabItem item = renderer.findItemForPart(part);
				if (item != null && !item.isShowing()) {
					return boldFont;
				}
			}
			return super.getFont(element);
		}

