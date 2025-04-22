        if (widthAtMinimumHeight == -1) {
            widthAtMinimumHeight = controlComputeSize(SWT.DEFAULT, minimumHeight - heightAdjustment).x;
        }

        return widthAtMinimumHeight;
    }
