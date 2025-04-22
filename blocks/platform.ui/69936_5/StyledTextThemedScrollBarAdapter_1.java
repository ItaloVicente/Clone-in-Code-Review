	static abstract class AbstractStyledTextScrollHandler extends AbstractScrollHandler
	implements ModifyListener, TextChangeListener {

		private final StyledText fStyledText;
		private AbstractThemedScrollBarAdapter fAbstractThemedScrollBarAdapter;
		private StyledTextContent fTextContent;
		private int fLastMax;
		private int fLastSelection;
		private int fCheckedTimes;

		protected AbstractStyledTextScrollHandler(StyledText styledText, ScrollBar scrollBar,
				IScrollBarSettings scrollBarSettings) {
			super(scrollBar, scrollBarSettings);
			this.fStyledText = styledText;
			this.fStyledText.setAlwaysShowScrollBars(true);
		}

		@Override
		protected void checkScrollbarInvisible() {
			if (this.fScrollBar == null || this.fScrollBar.isDisposed()
					|| !this.fScrollBarSettings.getScrollBarThemed()) {
				return;
			}
			if (this.fScrollBar.isVisible()) {
				if (fCheckedTimes > 20) {
					return;
				}
				fCheckedTimes++;
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						if (fStyledText.isDisposed()) {
							return;
						}
						if (!fStyledText.getAlwaysShowScrollBars()) {
							fStyledText.setAlwaysShowScrollBars(true);
						}
						if (fScrollBar != null && !fScrollBar.isDisposed()) {
							fScrollBar.setVisible(false);
						}
					}
				});
			}
		}

		@Override
		public void install(AbstractThemedScrollBarAdapter abstractThemedScrollBarAdapter) {
			super.install(abstractThemedScrollBarAdapter);
			fStyledText.addModifyListener(this);
			this.fAbstractThemedScrollBarAdapter = abstractThemedScrollBarAdapter;
			fTextContent = fStyledText.getContent();
			fTextContent.addTextChangeListener(this);
			if(fScrollBar != null){
				fLastMax = fScrollBar.getMaximum();
				fLastSelection = fScrollBar.getSelection();
			}
		}

		@Override
		public void uninstall(AbstractThemedScrollBarAdapter abstractThemedScrollBarAdapter, boolean disposing) {
			super.uninstall(abstractThemedScrollBarAdapter, disposing);
			fStyledText.removeModifyListener(this);
			if (fTextContent != null) {
				fTextContent.removeTextChangeListener(this);
				fTextContent = null;
			}
			this.fAbstractThemedScrollBarAdapter = null;
		}

		private void checkNeedUpdate() {
			if(fScrollBar != null){
				if (fLastMax != fScrollBar.getMaximum() || fLastSelection != fScrollBar.getSelection()) {
					this.fAbstractThemedScrollBarAdapter.fPainter.redrawScrollBars();
				}
			}
		}

		@Override
		public void modifyText(ModifyEvent e) {
			checkNeedUpdate();
		}

		@Override
		public void textSet(TextChangedEvent event) {
			checkNeedUpdate();
		}

		@Override
		public void textChanged(TextChangedEvent event) {
			checkNeedUpdate();
		}

		@Override
		public void textChanging(TextChangingEvent event) {

		}

		@Override
		public void paintControl(GC gc, Rectangle currClientArea, Scrollable scrollable) {
			if(fScrollBar != null){
				fLastMax = fScrollBar.getMaximum();
				fLastSelection = fScrollBar.getSelection();
			}

			if (fTextContent != null && fStyledText.getContent() != fTextContent) {
				fTextContent.removeTextChangeListener(this);
				fTextContent = fStyledText.getContent();
				fTextContent.addTextChangeListener(this);
			}
			super.paintControl(gc, currClientArea, scrollable);
		}
	}

