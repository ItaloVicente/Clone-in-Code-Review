package org.eclipse.ui.tests.api;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;

public class BadElementFactory implements IElementFactory {

	public static boolean fail = false;
	

	public static boolean failAttempted = false;

	public static class BadElementInstance implements IAdaptable,
			IPersistableElement {

		public static boolean fail = false;
		

		public static boolean failAttempted = false;


		@Override
		public Object getAdapter(Class adapter) {
			if (adapter.equals(IPersistableElement.class))
				return this;
			return null;
		}

		@Override
		public String getFactoryId() {
			return "org.eclipse.ui.tests.badFactory";
		}

		@Override
		public void saveState(IMemento memento) {
			if (fail) {
				failAttempted = true;
				throw new RuntimeException();
			}

		}

	}
;

	@Override
	public IAdaptable createElement(IMemento memento) {
		if (fail) {
			failAttempted = true;
			throw new RuntimeException();
		}
		return new BadElementInstance();
	}

}
