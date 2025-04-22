package org.eclipse.e4.ui.tests.application;

import javax.inject.Inject;
import javax.inject.Named;
import junit.framework.TestCase;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;

public class Bug299755Test extends TestCase {

	static class InjectionObject {

		@Inject
		Object object;
	}

	static class Out {

		@Inject
		private IEclipseContext context;

		public void setSelection(Object selection) {
			context.modify(IServiceConstants.ACTIVE_SELECTION, selection);
		}
	}

	static class In {

		@Inject
		InjectionObject object;

		private Object selection;

		@Inject
		@Optional
		void setSelection(
				@Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
			this.selection = selection;
		}

		public Object getSelection() {
			return selection;
		}
	}

	public void testBug299755() throws Exception {
		IEclipseContext windowContext = EclipseContextFactory.create();
		windowContext.set(Object.class.getName(), new Object());
		windowContext.set(InjectionObject.class.getName(),
				new ContextFunction() {
					@Override
					public Object compute(IEclipseContext context, String contextKey) {
						return ContextInjectionFactory.make(
								InjectionObject.class, context);
					}
				});
		windowContext.declareModifiable(IServiceConstants.ACTIVE_SELECTION);

		IEclipseContext outContext = windowContext.createChild();
		IEclipseContext inContext = windowContext.createChild();

		Out out = ContextInjectionFactory.make(Out.class, outContext);
		In in = ContextInjectionFactory.make(In.class, inContext);

		assertNull(in.getSelection());

		Object selection = new Object();
		out.setSelection(selection);

		assertEquals(selection, in.getSelection());
	}
}
