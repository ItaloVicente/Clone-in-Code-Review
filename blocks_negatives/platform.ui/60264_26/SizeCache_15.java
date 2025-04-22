    private int computeHeightAtMinimumWidth() {
        int minimumWidth = computeMinimumWidth();

        if (heightAtMinimumWidth == -1) {
            heightAtMinimumWidth = controlComputeSize(minimumWidth - widthAdjustment, SWT.DEFAULT).y;
        }

        return heightAtMinimumWidth;
    }

    private int computeWidthAtMinimumHeight() {
        int minimumHeight = computeMinimumHeight();

        if (widthAtMinimumHeight == -1) {
            widthAtMinimumHeight = controlComputeSize(SWT.DEFAULT, minimumHeight - heightAdjustment).x;
        }

        return widthAtMinimumHeight;
    }
