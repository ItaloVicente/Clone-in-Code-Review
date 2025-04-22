package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public abstract class DecoratorTestPart extends ViewPart {

	private static final int DELAY_TIME = 2000;// Wait 2 seconds

	public boolean waitingForDecoration = true;

	private long endTime;

	private ILabelProviderListener listener;

	public DecoratorTestPart() {
		super();
	}

	protected DecoratingLabelProvider getLabelProvider() {

		IDecoratorManager manager = PlatformUI.getWorkbench()
				.getDecoratorManager();
		manager.addListener(getDecoratorManagerListener());
		return new DecoratingLabelProvider(new TestLabelProvider(), manager);

	}

	private ILabelProviderListener getDecoratorManagerListener() {
		listener = new ILabelProviderListener() {

			@Override
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				endTime = System.currentTimeMillis() + DELAY_TIME;

			}
		};

		return listener;
	}

	public void readAndDispatchForUpdates() {
		while (System.currentTimeMillis() < endTime)
			Display.getCurrent().readAndDispatch();

	}

	public void setUpForDecorators() {
		endTime = System.currentTimeMillis() + DELAY_TIME;

	}

	@Override
	public void dispose() {
		PlatformUI.getWorkbench().getDecoratorManager()
				.removeListener(listener);

	}

}
