        text.setText(reason.getMessage());

        Composite buttonParent = new Composite(parent, SWT.NONE);
        buttonParent.setBackground(parent.getBackground());
        GridLayout buttonsLayout = new GridLayout();
        buttonsLayout.numColumns = 2;
        buttonsLayout.marginHeight = 0;
        buttonsLayout.marginWidth  = 0;
        buttonsLayout.horizontalSpacing = 0;
