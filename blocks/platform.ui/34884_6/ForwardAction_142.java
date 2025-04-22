package org.eclipse.ui.internal.navigator.framelist;

import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class BackAction extends FrameAction {

	private static final String ID = "org.eclipse.ui.framelist.back"; //$NON-NLS-1$
	
    public BackAction(FrameList frameList) {
        super(frameList);
        setId(ID);
        setText(FrameListMessages.Back_text);
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_BACK));
        setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_BACK_DISABLED));
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IFrameListHelpContextIds.BACK_ACTION);
        update();
    }

    private Frame getPreviousFrame() {
        FrameList list = getFrameList();
        return list.getFrame(list.getCurrentIndex() - 1);
    }

    private String getToolTipText(Frame previousFrame) {
        if (previousFrame != null) {
            String text = previousFrame.getToolTipText();
            if (text != null && text.length() > 0) {
                return NLS.bind(FrameListMessages.Back_toolTipOneArg, text);
            }
        }
        return FrameListMessages.Back_toolTip;
    }

    @Override
	public void run() {
        getFrameList().back();
    }

    @Override
	public void update() {
        super.update();
        Frame previousFrame = getPreviousFrame();
        setEnabled(previousFrame != null);
        setToolTipText(getToolTipText(previousFrame));
    }

}
