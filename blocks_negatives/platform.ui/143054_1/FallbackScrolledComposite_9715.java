    private void updatePageIncrement() {
        ScrollBar vbar = getVerticalBar();
        if (vbar != null) {
            Rectangle clientArea = getClientArea();
            int increment = clientArea.height - 5;
            vbar.setPageIncrement(increment);
        }
    }
