    /*
     * @return String the text contained in the input area of
     * the dialog.
     */
    String getText() {
        if (_log == null) {
            return "Empty entry.";
        } else {
            return _log;
        }
    }
