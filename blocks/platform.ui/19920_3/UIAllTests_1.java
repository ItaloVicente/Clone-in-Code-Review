package org.eclipse.e4.ui.internal.workbench.swt;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import org.eclipse.e4.core.di.InjectionException;
import org.eclipse.e4.core.internal.di.ThreadDomainHandler;
import org.eclipse.e4.ui.di.UIInject;
import org.eclipse.swt.widgets.Display;

@SuppressWarnings("restriction")
public class SWTThreadDomainHandler extends ThreadDomainHandler {

	@Override
	public Object handle(final Callable<Object> c) {
		final UIRunnable runnable = new UIRunnable(c);
		Display.getDefault().syncExec(runnable);
		if (runnable.getException() != null) {
			throw new InjectionException(runnable.getException());
		}
		return runnable.getResult();
	}

	private class UIRunnable implements Runnable {
		private Callable<Object> c;
		private Object result;
		private Exception exp;

		public UIRunnable(Callable<Object> c) {
			this.c = c;
		}

		public void run() {
			try {
				result = c.call();
			} catch (Exception e) {
				this.exp = e;
			}
		}

		public Exception getException() {
			return exp;
		}

		public Object getResult() {
			return result;
		}
	}

	@Override
	public boolean shouldHandle(Method method) {
		return method.getAnnotation(UIInject.class) != null;
	}
}
