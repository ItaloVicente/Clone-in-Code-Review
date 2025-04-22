
package org.eclipse.ui.internal;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class AnimatedTabFeedback extends ImageCycleFeedbackBase {

	private CTabItem tabItem;

	public AnimatedTabFeedback(Shell parentShell) {
		super(parentShell);
	}

	public AnimatedTabFeedback(Shell parentShell, CTabItem item,
			Image[] images) {
		super(parentShell, images);
		tabItem = item;
	}

	@Override
	public void initialize(AnimationEngine engine) {
		background = tabItem.getParent().getBackground();
		display = tabItem.getParent().getDisplay();
	}

	@Override
	public void saveStoppedImage() {
		stoppedImage = tabItem.getImage();
	}

	@Override
	public void setStoppedImage(Image image) {
		tabItem.setImage(image);
	}

	@Override
	public void showImage(Image image) {
		if (tabItem.isDisposed())
			return;
		tabItem.setImage(image);
	}

}
