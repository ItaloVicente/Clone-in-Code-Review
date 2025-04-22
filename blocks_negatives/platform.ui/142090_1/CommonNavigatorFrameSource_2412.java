        TreeFrame frame = super.createFrame(input);
        frame.setName(navigator.getTitle());
        frame.setToolTipText(navigator.getFrameToolTipText(input));
        return frame;
    }

    /**
     * Also updates the navigator's title.
     */
    @Override
