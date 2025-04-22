package org.eclipse.ui.internal.navigator.framelist;

import org.eclipse.ui.PlatformUI;

public class GoIntoAction extends FrameAction {

	private static final String ID = "org.eclipse.ui.framelist.goInto"; //$NON-NLS-1$

    public GoIntoAction(FrameList frameList) {
        super(frameList);
        setId(ID);
        setText(FrameListMessages.GoInto_text);
        setToolTipText(FrameListMessages.GoInto_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IFrameListHelpContextIds.GO_INTO_ACTION);
        update();
    }

    private Frame getSelectionFrame(int flags) {
        return getFrameList().getSource().getFrame(
                IFrameSource.SELECTION_FRAME, flags);
    }

    @Override
	public void run() {
        Frame selectionFrame = getSelectionFrame(IFrameSource.FULL_CONTEXT);
        if (selectionFrame != null) {
            getFrameList().gotoFrame(selectionFrame);
        }
    }

    @Override
	public void update() {
        super.update();
        Frame selectionFrame = getSelectionFrame(0);
        setEnabled(selectionFrame != null);
    }
}
