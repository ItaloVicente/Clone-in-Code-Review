package org.eclipse.ui.internal.decorators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.internal.ObjectContributorManager;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.util.Util;

public class LightweightDecoratorManager extends ObjectContributorManager {


	private class LightweightRunnable implements ISafeRunnable {
		private Object element;

		private DecorationBuilder decoration;

		private LightweightDecoratorDefinition decorator;

		void setValues(Object object, DecorationBuilder builder,
				LightweightDecoratorDefinition definition) {
			element = object;
			decoration = builder;
			decorator = definition;

		}

		@Override
		public void handleException(Throwable exception) {
			IStatus status = StatusUtil.newStatus(IStatus.ERROR, exception
					.getMessage(), exception);
			String message;
			if (decorator == null) {
				message = WorkbenchMessages.DecoratorError;
			} else {
				message = NLS.bind(WorkbenchMessages.DecoratorWillBeDisabled,
						decorator.getName());
			}
			WorkbenchPlugin.log(message, status);
			if (decorator != null) {
				decorator.crashDisable();
			}
			clearReferences();
		}

		@Override
		public void run() throws Exception {
			decorator.decorate(element, decoration);
			clearReferences();
		}

		void clearReferences() {
			decorator = null;
			element = null;// Clear the element
			decoration = null;
		}
	}

	private LightweightRunnable runnable = new LightweightRunnable();

	private LightweightDecoratorDefinition[] lightweightDefinitions;

	private static final LightweightDecoratorDefinition[] EMPTY_LIGHTWEIGHT_DEF = new LightweightDecoratorDefinition[0];

	LightweightDecoratorManager(LightweightDecoratorDefinition[] definitions) {
		super();
		lightweightDefinitions = definitions;
		buildContributors();
	}

	LightweightDecoratorDefinition[] getDefinitions() {
		return lightweightDefinitions;
	}

	private void buildContributors() {
		for (int i = 0; i < lightweightDefinitions.length; i++) {
			LightweightDecoratorDefinition decorator = lightweightDefinitions[i];
			String[] types = getTargetTypes(decorator);
			for (int j = 0; j < types.length; j++) {
				registerContributor(decorator, types[j]);
			}
		}
	}

	public boolean addDecorator(LightweightDecoratorDefinition decorator) {
		if (getLightweightDecoratorDefinition(decorator.getId()) == null) {
			LightweightDecoratorDefinition[] oldDefs = lightweightDefinitions;
			lightweightDefinitions = new LightweightDecoratorDefinition[lightweightDefinitions.length + 1];
			System.arraycopy(oldDefs, 0, lightweightDefinitions, 0,
					oldDefs.length);
			lightweightDefinitions[oldDefs.length] = decorator;
			String[] types = getTargetTypes(decorator);
			for (int i = 0; i < types.length; i++) {
				registerContributor(decorator, types[i]);
			}
			return true;
		}
		return false;
	}

	private String[] getTargetTypes(LightweightDecoratorDefinition decorator) {
		return decorator.getObjectClasses();
	}

	public boolean removeDecorator(LightweightDecoratorDefinition decorator) {
		int idx = getLightweightDecoratorDefinitionIdx(decorator.getId());
		if (idx != -1) {
			LightweightDecoratorDefinition[] oldDefs = lightweightDefinitions;
			Util
					.arrayCopyWithRemoval(
							oldDefs,
							lightweightDefinitions = new LightweightDecoratorDefinition[lightweightDefinitions.length - 1],
							idx);
			String[] types = getTargetTypes(decorator);
			for (int i = 0; i < types.length; i++) {
				unregisterContributor(decorator, types[i]);

			}
			return true;
		}
		return false;
	}

	private LightweightDecoratorDefinition getLightweightDecoratorDefinition(
			String decoratorId) {
		int idx = getLightweightDecoratorDefinitionIdx(decoratorId);
		if (idx != -1) {
			return lightweightDefinitions[idx];
		}
		return null;
	}

	private int getLightweightDecoratorDefinitionIdx(String decoratorId) {
		for (int i = 0; i < lightweightDefinitions.length; i++) {
			if (lightweightDefinitions[i].getId().equals(decoratorId)) {
				return i;
			}
		}
		return -1;
	}

	LightweightDecoratorDefinition[] enabledDefinitions() {
		ArrayList result = new ArrayList();
		for (int i = 0; i < lightweightDefinitions.length; i++) {
			if (lightweightDefinitions[i].isEnabled()) {
				result.add(lightweightDefinitions[i]);
			}
		}
		LightweightDecoratorDefinition[] returnArray = new LightweightDecoratorDefinition[result
				.size()];
		result.toArray(returnArray);
		return returnArray;
	}

	boolean hasEnabledDefinitions() {
		for (int i = 0; i < lightweightDefinitions.length; i++) {
			if (lightweightDefinitions[i].isEnabled()) {
				return true;
			}
		}
		return false;
	}

	void reset() {
		runnable.clearReferences();
	}

	void shutdown() {
		for (int i = 0; i < lightweightDefinitions.length; i++) {
			if (lightweightDefinitions[i].isEnabled()) {
				lightweightDefinitions[i].setEnabled(false);
			}
		}
	}

	LightweightDecoratorDefinition getDecoratorDefinition(String decoratorId) {
		for (int i = 0; i < lightweightDefinitions.length; i++) {
			if (lightweightDefinitions[i].getId().equals(decoratorId)) {
				return lightweightDefinitions[i];
			}
		}
		return null;
	}

	LightweightDecoratorDefinition[] getDecoratorsFor(Object element) {

		if (element == null) {
			return EMPTY_LIGHTWEIGHT_DEF;
		}

		List elements = new ArrayList(1);
		elements.add(element);
		LightweightDecoratorDefinition[] decoratorArray = EMPTY_LIGHTWEIGHT_DEF;
		List contributors = getContributors(elements);
		if (!contributors.isEmpty()) {
			Collection decorators = DecoratorManager.getDecoratorsFor(element,
					(DecoratorDefinition[]) contributors
							.toArray(new DecoratorDefinition[contributors
									.size()]));
			if (decorators.size() > 0) {
				decoratorArray = new LightweightDecoratorDefinition[decorators
						.size()];
				decorators.toArray(decoratorArray);
			}
		}

		return decoratorArray;
	}

	public void getDecorations(Object element, DecorationBuilder decoration) {

		LightweightDecoratorDefinition[] decorators = getDecoratorsFor(element);

		for (int i = 0; i < decorators.length; i++) {
			LightweightDecoratorDefinition dd = decorators[i];
			decoration.setCurrentDefinition(dd);
			decorate(element, decoration, dd);
		}
	}

	private void decorate(Object element, DecorationBuilder decoration,
			LightweightDecoratorDefinition decorator) {

		runnable.setValues(element, decoration, decorator);
		SafeRunner.run(runnable);
	}

	
	public DecorationResult getDecorationResult(Object object) {
		DecorationBuilder builder = new DecorationBuilder();
		getDecorations(object, builder);
		return builder.createResult();

	}

	@Override
	public void addExtension(IExtensionTracker tracker, IExtension extension) {
	}
}
