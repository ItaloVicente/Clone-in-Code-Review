        if (wHint == SWT.DEFAULT) {
            if (hHint == SWT.DEFAULT) {
                wHint = preferredWidth;
            } else {
                wHint = area / hHint;
            }
        }
