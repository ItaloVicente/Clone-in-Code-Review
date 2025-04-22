	static abstract class AbstractStyledTextScrollHandler extends AbstractScrollHandler
	implements ModifyListener, TextChangeListener {

		private final StyledText fStyledText;
		private AbstractThemedScrollBarAdapter fAbstractThemedScrollBarAdapter;
		private StyledTextContent fTextContent;
		private int fLastMax;
		private int fLastSelection;

		protected AbstractStyledTextScrollHandler(StyledText styledText, ScrollBar scrollBar,
				IScrollBarSettings scrollBarSettings) {
			super(scrollBar, scrollBarSettings);
			this.fStyledText = styledText;
		}

		@Override
		public void install(AbstractThemedScrollBarAdapter abstractThemedScrollBarAdapter) {
			super.install(abstractThemedScrollBarAdapter);
			fStyledText.addModifyListener(this);
			this.fAbstractThemedScrollBarAdapter = abstractThemedScrollBarAdapter;
			fTextContent = fStyledText.getContent();
			fTextContent.addTextChangeListener(this);
			fLastMax = fScrollBar.getMaximum();
			fLastSelection = fScrollBar.getSelection();
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
			if (fLastMax != fScrollBar.getMaximum() || fLastSelection != fScrollBar.getSelection()) {
				this.fAbstractThemedScrollBarAdapter.fPainter.redrawScrollBars();
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
			fLastMax = fScrollBar.getMaximum();
			fLastSelection = fScrollBar.getSelection();

			if (fTextContent != null && fStyledText.getContent() != fTextContent) {
				fTextContent.removeTextChangeListener(this);
				fTextContent = fStyledText.getContent();
				fTextContent.addTextChangeListener(this);
			}
			super.paintControl(gc, currClientArea, scrollable);
		}
	}

