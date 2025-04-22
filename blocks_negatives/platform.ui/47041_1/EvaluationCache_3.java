/*******************************************************************************
 * Copyright (c) 2003, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Bug 349224 Navigator content provider "appearsBefore" creates hard reference to named id - paul.fullbright@oracle.com
 *     C. Sean Young <csyoung@google.com> - Bug 436645
 *******************************************************************************/
package org.eclipse.ui.internal.navigator.extensions;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.internal.navigator.VisibilityAssistant;
import org.eclipse.ui.internal.navigator.VisibilityAssistant.VisibilityListener;

/**
 * A cache for evaluated {@link NavigatorContentDescriptor}.
 */
public class EvaluationCache implements VisibilityListener {


	private final Map<EvaluationReference<Object>, EvaluationValueReference<NavigatorContentDescriptor[]>> evaluations = new HashMap<>();
	private final Map<EvaluationReference<Object>, EvaluationValueReference<NavigatorContentDescriptor[]>> evaluationsWithOverrides = new HashMap<>();

	private final ReferenceQueue<Object> evaluationsQueue = new ReferenceQueue<>();
	private final ReferenceQueue<Object> evaluationsWithOverridesQueue = new ReferenceQueue<>();

	/**
	 * @param anAssistant the VisisbilityAssistant to register with, must be non-null
	 */
	public EvaluationCache(VisibilityAssistant anAssistant) {
		anAssistant.addListener(this);
	}

	private void cleanUpStaleEntries() {

		Reference<?> r;
		while ((r = evaluationsQueue.poll()) != null) {
			processStaleEntry(r, evaluations);
		}
		while ((r = evaluationsWithOverridesQueue.poll()) != null) {
			processStaleEntry(r, evaluationsWithOverrides);
		}
	}

	private static void processStaleEntry(Reference<?> r,
			Map<? extends Reference<?>, ? extends Reference<?>> fromMap) {
		if (r instanceof EvaluationReference) {
			EvaluationValueReference<?> oldVal = (EvaluationValueReference<?>) fromMap.remove(r);
			if (oldVal != null) {
				oldVal.clear();
			}
		}
		if (r instanceof EvaluationValueReference) {
			EvaluationReference<?> key = ((EvaluationValueReference<?>) r).getKey();
			if (key != null) {
				fromMap.remove(key);
			}
		}
	}

	private static NavigatorContentDescriptor[] getDescriptorsFromMap(Object anElement,
			Map<EvaluationReference<Object>, EvaluationValueReference<NavigatorContentDescriptor[]>> map) {
		EvaluationReference<Object> key = new EvaluationReference<>(anElement);
		NavigatorContentDescriptor[] cachedDescriptors = null;
		Reference<NavigatorContentDescriptor[]> cache = map.get(key);
		if (cache != null && (cachedDescriptors = cache.get()) == null) {
			EvaluationValueReference<NavigatorContentDescriptor[]> value = map.remove(key);
			if (value != null) {
				value.clear();
			}
		}
		return cachedDescriptors;
	}

	/**
	 * Finds the cached descriptors for the given key, or returns {@code null}
	 * if not currently in the cache.
	 *
	 * @param anElement
	 *            the key to lookup
	 * @param toComputeOverrides
	 *            whether overrides are to be considered
	 * @return the cached descriptors for the given key, or {@code null} if not
	 *         currently in the cache
	 */
	public final NavigatorContentDescriptor[] getDescriptors(Object anElement, boolean toComputeOverrides) {
		cleanUpStaleEntries();
		if (anElement == null)
			return null;

		if (toComputeOverrides) {
			return getDescriptorsFromMap(anElement, evaluations);
		}
		return getDescriptorsFromMap(anElement, evaluationsWithOverrides);
	}

	private static void setDescriptorsInMap(Object anElement, NavigatorContentDescriptor[] theDescriptors,
			Map<EvaluationReference<Object>, EvaluationValueReference<NavigatorContentDescriptor[]>> map,
			ReferenceQueue<Object> queue) {
		EvaluationReference<Object> key = new EvaluationReference<>(anElement, queue);
		EvaluationValueReference<NavigatorContentDescriptor[]> newValue =
				new EvaluationValueReference<>(theDescriptors, key, queue);
		EvaluationValueReference<NavigatorContentDescriptor[]> oldValue = map.put(key, newValue);
		if (oldValue != null) {
			newValue.swapKey(oldValue);
			oldValue.clear();
		}
	}

	/**
	 * Caches the given descriptors with the given key.
	 *
	 * @param anElement
	 *            the key to associate with the given descriptors
	 * @param theDescriptors
	 *            the descriptors to cache against the given key
	 * @param toComputeOverrides
	 *            whether overrides were considered in the computation of the
	 *            given descriptors
	 */
	public final void setDescriptors(Object anElement, NavigatorContentDescriptor[] theDescriptors,
			boolean toComputeOverrides) {
		cleanUpStaleEntries();
		if (anElement != null) {
			if (toComputeOverrides) {
				setDescriptorsInMap(anElement, theDescriptors, evaluations, evaluationsQueue);
			} else {
				setDescriptorsInMap(anElement, theDescriptors, evaluationsWithOverrides, evaluationsWithOverridesQueue);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * For an EvaluationCache, this means invalidating all cached descriptors.
	 */
	@Override
	public void onVisibilityOrActivationChange() {
		while (evaluationsQueue.poll() != null) {
		}
		while (evaluationsWithOverridesQueue.poll() != null) {
		}
		evaluations.clear();
		evaluationsWithOverrides.clear();
	}
}
