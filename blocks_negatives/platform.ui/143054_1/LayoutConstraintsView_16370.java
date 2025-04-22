                    try {
                        getSite().getPage().showView("org.eclipse.ui.tests.layout.constraints.LayoutConstraintsView",
                                "" + System.currentTimeMillis(), IWorkbenchPage.VIEW_ACTIVATE);
                    } catch (PartInitException e1) {
                        MessageDialog.openError(getSite().getShell(), "Error opening view", "Unable to open view.");
                    }
                }
            });
            buttonData.applyTo(newViewButton);

            GridLayoutFactory.fillDefaults().equalWidth(true).numColumns(3).applyTo(buttonBar);
        }
        GridDataFactory.fillDefaults().grab(true, false).span(2,1).applyTo(buttonBar);

        new Label(parent, SWT.NONE).setText("Min Width");
        minWidthText = createText(parent);

        new Label(parent, SWT.NONE).setText("Max Width (blank = unbounded)");
        maxWidthText = createText(parent);

        new Label(parent, SWT.NONE).setText("Quantized Width (blank = none)");
        quantizedWidthText = createText(parent);

        new Label(parent, SWT.NONE).setText("Min Height");
        minHeightText = createText(parent);

        new Label(parent, SWT.NONE).setText("Max Height (blank = unbounded)");
        maxHeightText = createText(parent);

        new Label(parent, SWT.NONE).setText("Quantized Height (blank = none)");
        quantizedHeightText = createText(parent);

        new Label(parent, SWT.NONE).setText("Fixed Area (blank = none");
        fixedAreaText = createText(parent);

        sampleImplementation = new Text(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        sampleImplementation.setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
        sampleImplementation.setTabs(4);
        GridDataFactory.fillDefaults().grab(true, true).span(2,1).applyTo(sampleImplementation);

        GridLayoutFactory.fillDefaults().numColumns(2).margins(LayoutConstants.getMargins()).generateLayout(parent);

        applyPressed();

    }

    /**
     *
     */
    protected void applyPressed() {
        minWidth = getInt(minWidthText);
        maxWidth = getInt(maxWidthText);
        quantizedWidth = getInt(quantizedWidthText);
        minHeight = getInt(minHeightText);
        maxHeight = getInt(maxHeightText);
        quantizedHeight = getInt(quantizedHeightText);
        fixedArea = getInt(fixedAreaText);

        StringBuilder result = new StringBuilder();
        sampleImplementation.setText(result.toString() +
                getSizeFlagsString() + computePreferredSizeString());

        updateLayout();
    }

    @Override
