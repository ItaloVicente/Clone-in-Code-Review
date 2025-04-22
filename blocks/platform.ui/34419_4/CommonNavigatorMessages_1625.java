package org.eclipse.ui.internal.navigator;

import org.eclipse.ui.internal.navigator.framelist.TreeFrame;
import org.eclipse.ui.internal.navigator.framelist.TreeViewerFrameSource;
import org.eclipse.ui.navigator.CommonNavigator;

public class CommonNavigatorFrameSource extends TreeViewerFrameSource {

    private CommonNavigator navigator;

    public CommonNavigatorFrameSource(CommonNavigator navigator) {
        super(navigator.getCommonViewer());
        this.navigator = navigator;
    }

    @Override
	protected TreeFrame createFrame(Object input) {
        TreeFrame frame = super.createFrame(input);
        frame.setName(navigator.getTitle());
        frame.setToolTipText(navigator.getFrameToolTipText(input));
        return frame;
    }

    @Override
	protected void frameChanged(TreeFrame frame) {
        super.frameChanged(frame);
        navigator.updateTitle();
    }
    
    
}
