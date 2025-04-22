package org.eclipse.ui.internal.navigator.extensions;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.internal.navigator.VisibilityAssistant;
import org.eclipse.ui.internal.navigator.VisibilityAssistant.VisibilityListener;

public class EvaluationCache implements VisibilityListener {


	private final Map<EvaluationReference<Object>, EvaluationValueReference<NavigatorContentDescriptor[]>> evaluations = new HashMap<>();
	private final Map<EvaluationReference<Object>, EvaluationValueReference<NavigatorContentDescriptor[]>> evaluationsWithOverrides = new HashMap<>();

	private final ReferenceQueue<Object> evaluationsQueue = new ReferenceQueue<>();
	private final ReferenceQueue<Object> evaluationsWithOverridesQueue = new ReferenceQueue<>();

	protected EvaluationCache(VisibilityAssistant anAssistant) {
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

	protected final NavigatorContentDescriptor[] getDescriptors(Object anElement) {
		return getDescriptors(anElement, true);
	}

	protected final void setDescriptors(Object anElement, NavigatorContentDescriptor[] theDescriptors) {
		setDescriptors(anElement, theDescriptors, true);
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

	protected final NavigatorContentDescriptor[] getDescriptors(Object anElement, boolean toComputeOverrides) {
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
				new EvaluationValueReference<NavigatorContentDescriptor[]>(theDescriptors, key, queue);
		EvaluationValueReference<NavigatorContentDescriptor[]> oldValue = map.put(key, newValue);
		if (oldValue != null) {
			newValue.swapKey(oldValue);
			oldValue.clear();
		}
	}

	protected final void setDescriptors(Object anElement, NavigatorContentDescriptor[] theDescriptors,
			boolean toComputeOverrides) {
		cleanUpStaleEntries();
		if (anElement != null) {
			if (toComputeOverrides)
				setDescriptorsInMap(anElement, theDescriptors, evaluations, evaluationsQueue);
			else
				setDescriptorsInMap(anElement, theDescriptors, evaluationsWithOverrides, evaluationsWithOverridesQueue);
		}
	}

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
