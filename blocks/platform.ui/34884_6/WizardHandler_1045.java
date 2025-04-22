package org.eclipse.ui.internal.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.internal.ExceptionHandler;

public class WidgetMethodHandler extends AbstractHandler implements
		IExecutableExtension {
	
	protected static final Class[] NO_PARAMETERS = new Class[0];

	public WidgetMethodHandler() {
		display = Display.getCurrent();
		if (display != null) {
			focusListener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					updateEnablement();
				}
			};
			display.addFilter(SWT.FocusIn, focusListener);
		}
	}
	
	void updateEnablement() {
		boolean rc = isHandled();
		if (rc != isEnabled()) {
			setBaseEnabled(rc);
		}
	}

	protected String methodName;
	private Listener focusListener;
	private Display display;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final Method methodToExecute = getMethodToExecute();
		if (methodToExecute != null) {
			try {
				final Control focusControl = Display.getCurrent()
						.getFocusControl();
				if ((focusControl instanceof Composite)
						&& ((((Composite) focusControl).getStyle() & SWT.EMBEDDED) != 0)) {
					try {
						final Object focusComponent = getFocusComponent();
						if (focusComponent != null) {
							Runnable methodRunnable = new Runnable() {
								@Override
								public void run() {
									try {
										methodToExecute.invoke(focusComponent);
									} catch (final IllegalAccessException e) {
									} catch (final InvocationTargetException e) {
										focusControl.getDisplay().asyncExec(
												new Runnable() {
													@Override
													public void run() {
														ExceptionHandler
																.getInstance()
																.handleException(
																		new ExecutionException(
																				"An exception occurred while executing " //$NON-NLS-1$
																						+ methodToExecute
																								.getName(),
																				e
																						.getTargetException()));
													}
												});
									}
								}
							};

							swingInvokeLater(methodRunnable);
						}
					} catch (final ClassNotFoundException e) {

					} catch (final NoSuchMethodException e) {
						throw new Error("Something is seriously wrong here"); //$NON-NLS-1$
					}

				} else {

					methodToExecute.invoke(focusControl);
				}

			} catch (IllegalAccessException e) {

			} catch (InvocationTargetException e) {
				throw new ExecutionException(
						"An exception occurred while executing " //$NON-NLS-1$
								+ methodToExecute.getName(), e
								.getTargetException());

			}
		}

		return null;
	}

	protected void swingInvokeLater(Runnable methodRunnable)
			throws ClassNotFoundException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		final Class swingUtilitiesClass = Class
				.forName("javax.swing.SwingUtilities"); //$NON-NLS-1$
		final Method swingUtilitiesInvokeLaterMethod = swingUtilitiesClass
				.getMethod("invokeLater", //$NON-NLS-1$
						new Class[] { Runnable.class });
		swingUtilitiesInvokeLaterMethod.invoke(swingUtilitiesClass,
				new Object[] { methodRunnable });
	}

	protected Object getFocusComponent() throws ClassNotFoundException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Class keyboardFocusManagerClass = null;
		try {
			keyboardFocusManagerClass = Class
					.forName("java.awt.KeyboardFocusManager"); //$NON-NLS-1$
		} catch (ClassNotFoundException e) {
		}
		if (keyboardFocusManagerClass != null) {
			final Method keyboardFocusManagerGetCurrentKeyboardFocusManagerMethod = keyboardFocusManagerClass
					.getMethod("getCurrentKeyboardFocusManager"); //$NON-NLS-1$
			final Object keyboardFocusManager = keyboardFocusManagerGetCurrentKeyboardFocusManagerMethod
					.invoke(keyboardFocusManagerClass);
			final Method keyboardFocusManagerGetFocusOwner = keyboardFocusManagerClass
					.getMethod("getFocusOwner"); //$NON-NLS-1$
			final Object focusComponent = keyboardFocusManagerGetFocusOwner
					.invoke(keyboardFocusManager);
			return focusComponent;
		}
		final Class focusManagerClass = Class
				.forName("javax.swing.FocusManager"); //$NON-NLS-1$
		final Method focusManagerGetCurrentManagerMethod = focusManagerClass
				.getMethod("getCurrentManager"); //$NON-NLS-1$
		final Object focusManager = focusManagerGetCurrentManagerMethod
		        .invoke(focusManagerClass);
		final Method focusManagerGetFocusOwner = focusManagerClass
		        .getMethod("getFocusOwner"); //$NON-NLS-1$
		final Object focusComponent = focusManagerGetFocusOwner
		        .invoke(focusManager);
		return focusComponent;

	}
	
	@Override
	public final boolean isHandled() {
		return getMethodToExecute() != null;
	}

	protected Method getMethodToExecute() {
		Display display = Display.getCurrent();
		if (display == null)
			return null;
		final Control focusControl = display.getFocusControl();
		Method method = null;

		if (focusControl != null) {
			final Class clazz = focusControl.getClass();
			try {
				method = clazz.getMethod(methodName, NO_PARAMETERS);
			} catch (NoSuchMethodException e) {
			}
		}

		if ((method == null)
				&& (focusControl instanceof Composite)
				&& ((((Composite) focusControl).getStyle() & SWT.EMBEDDED) != 0)) {
			try {
				final Object focusComponent = getFocusComponent();
				if (focusComponent != null) {
					final Class clazz = focusComponent.getClass();

					try {
						method = clazz.getMethod(methodName, NO_PARAMETERS);
					} catch (NoSuchMethodException e) {
					}
				}
			} catch (final ClassNotFoundException e) {

			} catch (final NoSuchMethodException e) {
				throw new Error("Something is seriously wrong here"); //$NON-NLS-1$
			} catch (IllegalAccessException e) {
				throw new Error("Something is seriously wrong here"); //$NON-NLS-1$
			} catch (InvocationTargetException e) {
				throw new Error("Something is seriously wrong here"); //$NON-NLS-1$
			}
		}

		return method;
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) {
		methodName = data.toString();
	}
	
	@Override
	public void dispose() {
		if (display!=null && !display.isDisposed()) {
			display.removeFilter(SWT.FocusIn, focusListener);
		}
		display = null;
		focusListener = null;
	}
}
