    /**
     * Handle the key release
     */
    public void handleKeyReleased(KeyEvent event) {
        if (event.keyCode == SWT.F2 && event.stateMask == 0 && isEnabled()) {
            run();
        }
    }
