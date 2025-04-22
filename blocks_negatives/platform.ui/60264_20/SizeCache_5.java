            Point newHeight = controlComputeSize(widthHint - widthAdjustment, SWT.DEFAULT);

            cachedHeightQuery = heightHint;
            cachedHeightResult = newHeight.y;

            return newHeight;
