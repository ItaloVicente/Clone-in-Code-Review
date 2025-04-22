	class BrowserSupportedTooltip extends ColumnViewerToolTipSupport {

		private Browser browser;
		private Shell shell;
		private TextLayout fTextLayout;

		protected BrowserSupportedTooltip(ColumnViewer viewer, int style, boolean manualActivation) {
			super(viewer, style, manualActivation);
		}

		@SuppressWarnings("restriction")
		@Override
		protected Composite createToolTipContentArea(Event event, Composite parent) {
			if (BrowserInformationControl.isAvailable(parent)) {
				shell = (Shell) parent;
				createContent(parent, event);
				return null;
			}
			return super.createToolTipContentArea(event, parent);
		}

		protected void createContent(Composite parent, Event event) {
			final AtomicReference<Point> sizeRef = new AtomicReference<>(new Point(400, 400));
			Composite contentComposite = new Composite(parent, SWT.NONE) {
				@Override
				public Point computeSize(int wHint, int hHint, boolean changed) {
					return sizeRef.get();
				}
			};
			contentComposite.setLayout(new FillLayout());

			browser = new Browser(contentComposite, SWT.NONE);
			browser.setJavascriptEnabled(false);

			Display display = parent.getDisplay();
			browser.setForeground(display.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
			browser.setBackground(display.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
			browser.addOpenWindowListener(e -> e.required = true);

			browser.setMenu(new Menu(shell, SWT.NONE));

			createTextLayout();

			String text = getText(event);
			text = fgStyleSheet + text + "</p></body></html>"; //$NON-NLS-1$
			Point size = computeSizeHint(text);
			sizeRef.set(size);
			browser.setText(text);
		}

		private void createTextLayout() {
			fTextLayout = new TextLayout(browser.getDisplay());

			String symbolicFontName = JFaceResources.DIALOG_FONT;
			Font font = JFaceResources.getFont(symbolicFontName);
			fTextLayout.setFont(font);
			fTextLayout.setWidth(-1);

			fTextLayout.setText("    "); //$NON-NLS-1$
			int tabWidth = fTextLayout.getBounds().width;
			fTextLayout.setTabs(new int[] { tabWidth });
			fTextLayout.setText(""); //$NON-NLS-1$
		}

		@SuppressWarnings("restriction")
		public Point computeSizeHint(String text) {
			Point sizeConstraints = new Point(800, 600);
			Rectangle trim = shell.computeTrim(0, 0, 0, 0);
			int height = trim.height;

			TextPresentation presentation = new TextPresentation();
			try (HTML2TextReader reader = new HTML2TextReader(new StringReader(text), presentation)) {
				text = reader.getString();
			} catch (IOException e) {
				text = ""; //$NON-NLS-1$
			}

			fTextLayout.setText(text);
			fTextLayout.setWidth(sizeConstraints == null ? SWT.DEFAULT : sizeConstraints.x - trim.width);

			Rectangle bounds = fTextLayout.getBounds(); // does not return
			int lineCount = fTextLayout.getLineCount();
			int textWidth = 0;
			for (int i = 0; i < lineCount; i++) {
				Rectangle rect = fTextLayout.getLineBounds(i);
				int lineWidth = rect.x + rect.width;
				textWidth = Math.max(textWidth, lineWidth);
			}
			bounds.width = textWidth;
			fTextLayout.setText(""); //$NON-NLS-1$

			int minWidth = bounds.width;
			height = height + bounds.height;

			minWidth += 5;
			height += 5;

			if (sizeConstraints != null) {
				if (sizeConstraints.x != SWT.DEFAULT) {
					minWidth = Math.min(sizeConstraints.x, minWidth + trim.width);
				}
				if (sizeConstraints.y != SWT.DEFAULT) {
					height = Math.min(sizeConstraints.y, height);
				}
			}

			int width = Math.max(MIN_WIDTH, minWidth);
			height = Math.max(MIN_HEIGHT, height);

			return new Point(width, height);
		}

		private static final String fgStyleSheet= "<html><head><style CHARSET=\"ISO-8859-1\" TYPE=\"text/css\">" + //$NON-NLS-1$
				"/* Font definitions */\n" + //$NON-NLS-1$
				"body, h1, h2, h3, h4, h5, h6, p, table, td, caption, th, ul, ol, dl, li, dd, dt {font-family: sans-serif; font-size: 9pt }\n" + //$NON-NLS-1$
				"pre				{ font-family: monospace; font-size: 9pt }\n" + //$NON-NLS-1$
				"\n" + //$NON-NLS-1$
				"/* Margins */\n" + //$NON-NLS-1$
				"body	     { overflow: auto; margin-top: 0; margin-bottom: 4; margin-left: 3; margin-right: 0 }\n" + //$NON-NLS-1$
				"h1           { margin-top: 5; margin-bottom: 1 }	\n" + //$NON-NLS-1$
				"h2           { margin-top: 25; margin-bottom: 3 }\n" + //$NON-NLS-1$
				"h3           { margin-top: 20; margin-bottom: 3 }\n" + //$NON-NLS-1$
				"h4           { margin-top: 20; margin-bottom: 3 }\n" + //$NON-NLS-1$
				"h5           { margin-top: 0; margin-bottom: 0 }\n" + //$NON-NLS-1$
				"p            { margin-top: 10px; margin-bottom: 10px }\n" + //$NON-NLS-1$
				"pre	         { margin-left: 6 }\n" + //$NON-NLS-1$
				"ul	         { margin-top: 0; margin-bottom: 10 }\n" + //$NON-NLS-1$
				"li	         { margin-top: 0; margin-bottom: 0 } \n" + //$NON-NLS-1$
				"li p	     { margin-top: 0; margin-bottom: 0 } \n" + //$NON-NLS-1$
				"ol	         { margin-top: 0; margin-bottom: 10 }\n" + //$NON-NLS-1$
				"dl	         { margin-top: 0; margin-bottom: 10 }\n" + //$NON-NLS-1$
				"dt	         { margin-top: 0; margin-bottom: 0; font-weight: bold }\n" + //$NON-NLS-1$
				"dd	         { margin-top: 0; margin-bottom: 0 }\n" + //$NON-NLS-1$
				"\n" + //$NON-NLS-1$
				"/* Styles and colors */\n" + //$NON-NLS-1$
				"a:link	     { color: #0000FF }\n" + //$NON-NLS-1$
				"a:hover	     { color: #000080 }\n" + //$NON-NLS-1$
				"a:visited    { text-decoration: underline }\n" + //$NON-NLS-1$
				"h4           { font-style: italic }\n" + //$NON-NLS-1$
				"strong	     { font-weight: bold }\n" + //$NON-NLS-1$
				"em	         { font-style: italic }\n" + //$NON-NLS-1$
				"var	         { font-style: italic }\n" + //$NON-NLS-1$
				"th	         { font-weight: bold }\n" + //$NON-NLS-1$
				"</style></head><body><p>"; //$NON-NLS-1$

		private static final int MIN_WIDTH = 50;

		private static final int MIN_HEIGHT = 30;
	}

