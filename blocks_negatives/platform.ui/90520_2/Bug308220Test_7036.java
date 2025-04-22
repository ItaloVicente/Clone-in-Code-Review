/*******************************************************************************
 * Copyright (c) 2010, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <Lars.Vogel@gmail.com> - Bug 440893
 ******************************************************************************/
package org.eclipse.e4.ui.tests.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.junit.Test;

public class Bug299755Test {

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

	@Test
	public void testBug299755() throws Exception {
		IEclipseContext windowContext = EclipseContextFactory.create();
		windowContext.set(Object.class, new Object());
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
