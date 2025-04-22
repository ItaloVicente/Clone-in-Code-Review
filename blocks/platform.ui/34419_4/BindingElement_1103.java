
package org.eclipse.ui.internal.keys;

import java.util.List;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class WorkbenchKeyboard {
	private KeyBindingDispatcher delegate;

	static class KeyDownFilter implements Listener {
		private KeyBindingDispatcher.KeyDownFilter delegate;

		@Override
		public void handleEvent(Event event) {
			delegate.handleEvent(event);
		}

		@Override
		public String toString() {
			return delegate.toString();
		}

		public KeyDownFilter(KeyBindingDispatcher.KeyDownFilter filter) {
			this.delegate = filter;
		}

	}

	@Override
	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	public KeyDownFilter getKeyDownFilter() {
		return new KeyDownFilter(delegate.getKeyDownFilter());
	}

	public boolean press(List potentialKeyStrokes, Event event) {
		return delegate.press(potentialKeyStrokes, event);
	}

	public void setContext(IEclipseContext context) {
		delegate.setContext(context);
	}

	public WorkbenchKeyboard(KeyBindingDispatcher kbd) {
		delegate = kbd;
	}

	public static List generatePossibleKeyStrokes(Event event) {
		return KeyBindingDispatcher.generatePossibleKeyStrokes(event);
	}
}
