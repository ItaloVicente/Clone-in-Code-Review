/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.core.internal.contexts.debug.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.eclipse.e4.core.internal.contexts.Computation;
import org.eclipse.e4.core.internal.contexts.EclipseContext;

/**
 * Note that diff() excludes "transient" contexts - contexts that are not present
 * in either "old" or "new" snapshots.
 */
public class ContextSnapshot {

	private Map<EclipseContext, Set<Computation>> snapshotOriginal;

	public ContextSnapshot() {
		snapshotOriginal = record();
	}

	private Map<EclipseContext, Set<Computation>> record() {
		Map<EclipseContext, Set<Computation>> snapshot = new HashMap<EclipseContext, Set<Computation>>();
		Set<EclipseContext> contexts = AllocationRecorder.getDefault().getContexts();
		for (EclipseContext context : contexts) {
			record(snapshot, context);
		}
		return snapshot;
	}

	private void record(Map<EclipseContext, Set<Computation>> snapshot, EclipseContext context) {
		Set<Computation> listeners = context.getListeners();
		Set<Computation> localListeners = new HashSet<Computation>(listeners.size());
		snapshot.put(context, localListeners);

		Set<EclipseContext> children = context.getChildren();
		if (children == null)
			return;
		for (EclipseContext child : context.getChildren()) {
			record(snapshot, child);
		}
	}

	public Map<EclipseContext, Set<Computation>> diff() {
		Map<EclipseContext, Set<Computation>> snapshotNew = record();

		for (Iterator<EclipseContext> i = snapshotNew.keySet().iterator(); i.hasNext();) {
			EclipseContext context = i.next();
			if (!snapshotOriginal.containsKey(context))
				i.remove();
		}

		for (Iterator<EclipseContext> i = snapshotNew.keySet().iterator(); i.hasNext();) {
			EclipseContext context = i.next();
			Set<Computation> oldComputation = snapshotOriginal.get(context);
			Set<Computation> newComputation = snapshotNew.get(context);
			newComputation.removeAll(oldComputation);
			if (newComputation.isEmpty())
				i.remove();
		}

		if (snapshotNew.isEmpty())
			return null;
		return snapshotNew;
	}
}
