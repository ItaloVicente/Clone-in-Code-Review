        if (useGroup) {
            Control control = getRadioBoxControl(parent);
            GridData gd = new GridData(GridData.FILL_HORIZONTAL);
            control.setLayoutData(gd);
        } else {
            Control control = getLabelControl(parent);
            GridData gd = new GridData();
            gd.horizontalSpan = numColumns;
            control.setLayoutData(gd);
            control = getRadioBoxControl(parent);
            gd = new GridData();
            gd.horizontalSpan = numColumns;
            gd.horizontalIndent = indent;
            control.setLayoutData(gd);
        }

    }

    @Override
