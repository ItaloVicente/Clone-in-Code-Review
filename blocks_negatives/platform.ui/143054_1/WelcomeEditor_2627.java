            addListeners(styledText);

            Label spacer = new Label(infoArea, SWT.NONE);
            spacer.setBackground(background);
            gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
            gd.horizontalSpan = 2;
            spacer.setLayoutData(gd);

            menuMgr.add(copyAction);
            styledText.setMenu(menuMgr.createContextMenu(styledText));
        }

        lastText = sampleStyledText;
        this.scrolledComposite.setContent(infoArea);
        Point p = infoArea.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        this.scrolledComposite.setMinHeight(p.y);
        if (wrapped) {
            this.scrolledComposite.setMinWidth(WRAP_MIN_WIDTH);
        } else {
            this.scrolledComposite.setMinWidth(p.x);
        }
        this.scrolledComposite.setExpandHorizontal(true);
        this.scrolledComposite.setExpandVertical(true);

        if (wrapped && (imageLabel != null)) {
            Rectangle bounds = imageLabel.getBounds();
            final int adjust = HINDENT + bounds.width + layout.verticalSpacing
                    + (layout.marginWidth * 2);
            final int adjustFirst = HINDENT + (layout.marginWidth * 2);
            infoArea.addListener(SWT.Resize, event -> {
			    int w = scrolledComposite.getClientArea().width;
			    if (w < WRAP_MIN_WIDTH) {
			        w = WRAP_MIN_WIDTH;
			    }
			    for (int i = 0; i < texts.size(); i++) {
			        int extent;
			        if (i == 0) {
