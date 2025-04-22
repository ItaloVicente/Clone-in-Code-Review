        Point preferred = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        if (preferred.y * size.y > 0) {
            if (size.y / preferred.y > 1) {
                preferred.x /= (size.y / preferred.y);
            }
        }
