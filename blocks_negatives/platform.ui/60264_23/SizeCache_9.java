            Point widthResult = controlComputeSize(SWT.DEFAULT, heightHint - heightAdjustment);

            cachedWidthQuery = heightHint;
            cachedWidthResult = widthResult.x;

            return widthResult;
