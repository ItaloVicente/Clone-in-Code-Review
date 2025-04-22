package org.eclipse.ui.internal.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.ExceptionHandler;

public class SelectAllHandler extends WidgetMethodHandler {

	private static final Class[] METHOD_PARAMETERS = { Point.class };

	@Override
	public final Object execute(final ExecutionEvent event)
			throws ExecutionException {
		final Method methodToExecute = getMethodToExecute();
		if (methodToExecute != null) {
			try {
				final Control focusControl = Display.getCurrent()
						.getFocusControl();
				
				final int numParams = methodToExecute.getParameterTypes().length;

				if ((focusControl instanceof Composite)
                        && ((((Composite) focusControl).getStyle() & SWT.EMBEDDED) != 0)) {
					
					if (numParams != 0) {
						return null;
					}

					try {
						final Object focusComponent = getFocusComponent();
						if (focusComponent != null) {
							Runnable methodRunnable = new Runnable() {
								@Override
								public void run() {
									try {
										methodToExecute.invoke(focusComponent);
										focusControl.getDisplay().asyncExec(
												new Runnable() {
													@Override
													public void run() {
														if (!focusControl
																.isDisposed()) {
															focusControl
																	.notifyListeners(
																			SWT.Selection,
																			null);
														}
													}
												});
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
				} else if (numParams == 0) {
					methodToExecute.invoke(focusControl);
					focusControl.notifyListeners(SWT.Selection, null);

				} else if (numParams == 1) {
					final Method textLimitAccessor = focusControl.getClass()
							.getMethod("getTextLimit", NO_PARAMETERS); //$NON-NLS-1$
					final Integer textLimit = (Integer) textLimitAccessor
							.invoke(focusControl);
					final Object[] parameters = { new Point(0, textLimit
							.intValue()) };
					methodToExecute.invoke(focusControl, parameters);
					if (!(focusControl instanceof Combo)) {
						focusControl.notifyListeners(SWT.Selection, null);
					}

				} else {
					throw new ExecutionException(
							"Too many parameters on select all", new Exception()); //$NON-NLS-1$

				}

			} catch (IllegalAccessException e) {

			} catch (InvocationTargetException e) {
				throw new ExecutionException(
						"An exception occurred while executing " //$NON-NLS-1$
								+ getMethodToExecute(), e.getTargetException());

			} catch (NoSuchMethodException e) {

			}
		}

		return null;
	}

	@Override
	protected Method getMethodToExecute() {
		Method method = super.getMethodToExecute();

		if (method == null) {
			final Control focusControl = Display.getCurrent().getFocusControl();
			if (focusControl != null) {
				try {
					method = focusControl.getClass().getMethod("setSelection", //$NON-NLS-1$
							METHOD_PARAMETERS);
				} catch (NoSuchMethodException e) {
				}
			}
		}

		return method;
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) {
		methodName = "selectAll"; //$NON-NLS-1$
	}
}
