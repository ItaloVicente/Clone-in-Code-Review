        String widget = label.toString();
        Point size = label.getSize();
        String labelText = label.getText();
        if (labelText == null || labelText.length() == 0)
        	return;
        Point preferred = label.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        if (preferred.y * size.y > 0) {
            preferred.y /= countLines(label.getText());
            if (size.y / preferred.y > 1) {
                preferred.x /= (size.y / preferred.y);
            }
        }
        String message = new StringBuilder("Warning: ").append(widget).append(
                "\n\tActual Width -> ").append(size.x).append(
                "\n\tRecommended Width -> ").append(preferred.x).toString();
        if (preferred.x > size.x) {
            label.getShell().dispose();
