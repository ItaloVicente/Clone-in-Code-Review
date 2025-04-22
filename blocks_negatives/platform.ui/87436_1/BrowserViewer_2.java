        if (showToolbar || showURLbar) {
            Composite toolbarComp = new Composite(this, SWT.NONE);
            toolbarComp.setLayout(new ToolbarLayout());
            toolbarComp.setLayoutData(new GridData(
                  GridData.VERTICAL_ALIGN_BEGINNING
                  | GridData.FILL_HORIZONTAL));

            if (showToolbar)
                createToolbar(toolbarComp);

				if (showURLbar)
                createLocationBar(toolbarComp);

				if (showToolbar | showURLbar) {
				    busy = new BusyIndicator(toolbarComp, SWT.NONE);
				    busy.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
				    busy.addMouseListener(new MouseListener() {
						@Override
						public void mouseDoubleClick(MouseEvent e) {
						}
