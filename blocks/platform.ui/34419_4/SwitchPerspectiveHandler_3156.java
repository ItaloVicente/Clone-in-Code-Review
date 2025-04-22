package org.eclipse.e4.demo.e4photo;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;

public class RealmFunction extends ContextFunction {

	public Object compute(IEclipseContext context, String contextKey) {
		return new LockRealm();
	}

}
