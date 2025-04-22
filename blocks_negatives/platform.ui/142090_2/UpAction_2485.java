    String getToolTipText(Frame parentFrame) {
        if (parentFrame != null) {
            String text = parentFrame.getToolTipText();
            if (text != null && text.length() > 0) {
                return NLS.bind(FrameListMessages.Up_toolTipOneArg, text);
            }
        }
        return FrameListMessages.Up_toolTip;
