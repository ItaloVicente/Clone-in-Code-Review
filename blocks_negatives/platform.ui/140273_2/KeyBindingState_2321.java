    /**
     * Updates the text of the status line of the associated shell with the
     * current sequence.
     */
    private void updateStatusLines() {
        StatusLineContributionItem statusLine = getStatusLine();
        if (statusLine != null) {
            statusLine.setText(getCurrentSequence().format());
        }
    }
