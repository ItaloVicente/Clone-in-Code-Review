package org.eclipse.ui.internal.navigator.framelist;

public interface IFrameSource {

    public static final int CURRENT_FRAME = 0x0001;

    public static final int SELECTION_FRAME = 0x0002;

    public static final int PARENT_FRAME = 0x0003;

    public static final int FULL_CONTEXT = 0x0001;

    public Frame getFrame(int whichFrame, int flags);
}
