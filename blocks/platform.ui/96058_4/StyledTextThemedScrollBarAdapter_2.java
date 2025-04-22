
		@Override
		protected void notifyScrollbarSelectionChanged(Scrollable scrollable, int detail) {
			super.notifyScrollbarSelectionChanged(scrollable, detail);
			StyledText styledText = (StyledText) scrollable;
			syncStyledTextDrawing(styledText);
		}
