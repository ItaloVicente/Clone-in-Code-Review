			}
		});
	}

	private Composite createInfoArea(Composite parent) {
		this.scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL
				| SWT.H_SCROLL);
		this.scrolledComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		final Composite infoArea = new Composite(this.scrolledComposite,
				SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 10;
		layout.verticalSpacing = 5;
		layout.numColumns = 2;
		infoArea.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		infoArea.setLayoutData(data);
		boolean wrapped = parser.isFormatWrapped();
		int HINDENT = 20;

		Display display = parent.getDisplay();
		Color background = JFaceColors.getBannerBackground(display);
		Color foreground = JFaceColors.getBannerForeground(display);
		infoArea.setBackground(background);

		int textStyle = SWT.MULTI | SWT.READ_ONLY;
		if (wrapped) {
			textStyle = textStyle | SWT.WRAP;
		}
		StyledText sampleStyledText = null;
		WelcomeItem item = getIntroItem();
		if (item != null) {
			StyledText styledText = new StyledText(infoArea, textStyle);
			this.texts.add(styledText);
			sampleStyledText = styledText;
			styledText.setCursor(null);
			JFaceColors.setColors(styledText, foreground, background);
			styledText.setText(getIntroItem().getText());
			setBoldRanges(styledText, item.getBoldRanges());
			setLinkRanges(styledText, item.getActionRanges());
			setLinkRanges(styledText, item.getHelpRanges());
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 2;
			gd.horizontalIndent = HINDENT;
			gd.verticalAlignment = GridData.VERTICAL_ALIGN_BEGINNING;
			styledText.setLayoutData(gd);
			styledText.setData(item);
			addListeners(styledText);

			Label spacer = new Label(infoArea, SWT.NONE);
			spacer.setBackground(background);
			gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
			gd.horizontalSpan = 2;
			spacer.setLayoutData(gd);
		}
		firstText = sampleStyledText;

		Label imageLabel = null;
