
package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.internal.navigator.CustomAndExpression;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.navigator.ILinkHelper;

public class LinkHelperDescriptor implements ILinkHelperExtPtConstants {

	private final IConfigurationElement configElement;

	private String id;

	private Expression editorInputEnablement;

	private Expression selectionEnablement;

	private boolean hasLinkHelperFailedCreation;

		Assert.isNotNull(aConfigElement,
				"LinkHelperRegistry.Descriptor objects cannot be null."); //$NON-NLS-1$
		Assert
				.isLegal(LINK_HELPER.equals(aConfigElement.getName()),
						"LinkHelperRegistry.Descriptor objects must have the name \"linkHelper\"."); //$NON-NLS-1$
		configElement = aConfigElement;
		init();
	}

	void init() {
		id = configElement.getAttribute(ATT_ID);
		IConfigurationElement[] expressions = this.configElement
				.getChildren(EDITOR_INPUT_ENABLEMENT);
		Assert
				.isLegal(
						expressions.length == 1,
						"The linkHelper extension point requires exactly one editorInputEnablement child."); //$NON-NLS-1$

		editorInputEnablement = new CustomAndExpression(expressions[0]);

		expressions = configElement.getChildren(SELECTION_ENABLEMENT);
		if (expressions.length > 0) {
			if (expressions[0].getChildren() != null
					&& expressions[0].getChildren().length > 0) {

				selectionEnablement = new CustomAndExpression(expressions[0]);

			}
		}
	}

	public String getId() {
		return id;
	}

	public ILinkHelper createLinkHelper() {
		if (hasLinkHelperFailedCreation)
			return SkeletonLinkHelper.INSTANCE;
		final ILinkHelper[] helper = new ILinkHelper[1];
		SafeRunner.run(new NavigatorSafeRunnable(configElement) {
			@Override
			public void run() throws Exception {
				helper[0] = (ILinkHelper) configElement.createExecutableExtension(ATT_CLASS);
			}
		});
		if (helper[0] != null)
			return helper[0];
		hasLinkHelperFailedCreation = true;
		return SkeletonLinkHelper.INSTANCE;
	}

	public boolean isEnabledFor(IEditorInput anInput) {
		if (editorInputEnablement == null || anInput == null) {
			return false;
		}
		IEvaluationContext context = NavigatorPlugin.getEvalContext(anInput);
		return NavigatorPlugin.safeEvaluate(editorInputEnablement, context) == EvaluationResult.TRUE;
	}

	public boolean isEnabledFor(Object anObject) {
		if (selectionEnablement == null) {
			return false;
		}
		IEvaluationContext context = NavigatorPlugin.getEvalContext(anObject);
		return NavigatorPlugin.safeEvaluate(selectionEnablement, context) == EvaluationResult.TRUE;
	}
}
