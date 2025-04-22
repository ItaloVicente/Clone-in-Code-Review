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
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.junit.Test;

public class Bug308220Test {

	static class WindowService {
		Object activePart;

		@Inject
		public void setActivePart(
				@Named(IServiceConstants.ACTIVE_PART) @Optional Object part) {
			activePart = part;
		}
	}

	@Test
	public void testBug308220() throws Exception {
		IEclipseContext app = EclipseContextFactory.create();

		app.set(IServiceConstants.ACTIVE_PART, new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				IEclipseContext childContext = context.getActiveChild();
				if (childContext == null) {
					return null;
				}

				while (childContext != null) {
					context = childContext;
					childContext = context.getActiveChild();
				}
				return context.getLocal(Object.class.getName());
			}
		});

		app.runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(IEclipseContext context) {
				context.get(IServiceConstants.ACTIVE_PART);
				return true;
			}
		});

		IEclipseContext windowA = app.createChild();
		IEclipseContext windowB = app.createChild();

		Object o1 = new Object();
		Object o2 = new Object();

		IEclipseContext part = windowA.createChild();
		part.set(Object.class.getName(), o1);
		part.activate();
		windowA.activate();

		WindowService windowServiceA = ContextInjectionFactory
				.make(WindowService.class, windowA);
		WindowService windowServiceB = ContextInjectionFactory
				.make(WindowService.class, windowB);

		assertEquals(o1, windowServiceA.activePart);
		assertNull(windowServiceB.activePart);

		part.set(Object.class.getName(), o2);

		assertEquals(o2, windowServiceA.activePart);
		assertNull(windowServiceB.activePart);
	}
}
