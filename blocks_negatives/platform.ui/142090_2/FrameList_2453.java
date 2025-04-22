    }

    /**
     * Returns the number of frames in the frame list.
     * @return the number of frames.
     */
    public int size() {
        return frames.size();
    }

    /**
     * Replaces the current frame in this list with the current frame
     * from the frame source.  No event is fired.
     */
    public void updateCurrentFrame() {
        Assert.isTrue(current >= 0);
        Frame frame = source.getFrame(IFrameSource.CURRENT_FRAME,
                IFrameSource.FULL_CONTEXT);
        frame.setParent(this);
        frame.setIndex(current);
        frames.set(current, frame);
    }
