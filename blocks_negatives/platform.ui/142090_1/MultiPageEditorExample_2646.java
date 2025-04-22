                setFont();
            }
        });

        int index = addPage(composite);
        setPageText(index, MessageUtil.getString("Properties")); //$NON-NLS-1$
    }

    /**
     * Creates page 2 of the multi-page editor,
     * which shows the sorted text.
     */
    void createPage2() {
        Composite composite = new Composite(getContainer(), SWT.NONE);
        FillLayout layout = new FillLayout();
        composite.setLayout(layout);
        text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
        text.setEditable(false);

        int index = addPage(composite);
        setPageText(index, MessageUtil.getString("Preview")); //$NON-NLS-1$
    }

    /**
     * Creates the pages of the multi-page editor.
     */
    @Override
