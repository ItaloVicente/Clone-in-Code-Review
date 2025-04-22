    private void init() {
        Frame frame = source.getFrame(IFrameSource.CURRENT_FRAME, 0);
        frame.setParent(this);
        frame.setIndex(0);
        frames = new ArrayList<>();
        frames.add(frame);
        current = 0;
    }

