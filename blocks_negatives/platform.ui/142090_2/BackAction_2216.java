    private String getToolTipText(Frame previousFrame) {
        if (previousFrame != null) {
            String text = previousFrame.getToolTipText();
            if (text != null && text.length() > 0) {
                return NLS.bind(FrameListMessages.Back_toolTipOneArg, text);
            }
        }
        return FrameListMessages.Back_toolTip;
    }
