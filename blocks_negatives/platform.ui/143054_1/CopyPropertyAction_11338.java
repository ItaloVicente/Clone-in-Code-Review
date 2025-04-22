    private void setClipboard(String text) {
        try {
            Object[] data = new Object[] { text };
            Transfer[] transferTypes = new Transfer[] { TextTransfer
                    .getInstance() };
            clipboard.setContents(data, transferTypes);
        } catch (SWTError e) {
            if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
