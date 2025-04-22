package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;

public class BackgroundColorDecorator implements ILightweightLabelDecorator {

	public static final String ID = "org.eclipse.ui.tests.backgroundDecorator";

	public static Color color;


	@Override
	public void decorate(Object element, IDecoration decoration) {

		if(color == null){
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					setUpColor();

				}
			});

		}
		decoration.setBackgroundColor(color);

	}

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}
	public static void setUpColor(){
		color = PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_CYAN);
	}


}
