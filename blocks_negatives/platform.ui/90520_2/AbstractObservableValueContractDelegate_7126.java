/*******************************************************************************
 * Copyright (c) 2007, 2014 Brad Reynolds and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Brad Reynolds - initial API and implementation
 *     Simon Scholz <simon.scholz@vogella.com> - Bug 444829
 ******************************************************************************/

package org.eclipse.jface.databinding.conformance.delegate;

import org.eclipse.core.databinding.observable.IObservable;

/**
 * Abstract implementation of {@link IObservableContractDelegate}.
 *
 * @since 1.1
 */
public abstract class AbstractObservableContractDelegate implements
		IObservableContractDelegate {
	@Override
	public void setUp() {
	}

	@Override
	public void tearDown() {
	}

	@Override
	public void change(IObservable observable) {
	}

	@Override
	public void setStale(IObservable observable, boolean stale) {
	}
}
