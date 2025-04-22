            Composite composite = (Composite) super.createDialogArea(parent);
            enabler = new ActivityEnabler(workingCopy, strings);
            Control enablerControl = enabler.createControl(composite);
            enablerControl.setLayoutData(new GridData(GridData.FILL_BOTH));
            return composite;
        }

        @Override
