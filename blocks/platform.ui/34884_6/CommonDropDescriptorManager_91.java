
package org.eclipse.ui.internal.navigator.dnd;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.internal.navigator.CustomAndExpression;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.internal.navigator.extensions.INavigatorContentExtPtConstants;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;

public final class CommonDropAdapterDescriptor implements
		INavigatorContentExtPtConstants {

	private final IConfigurationElement element;

	private final INavigatorContentDescriptor contentDescriptor;

	private Expression dropExpr;

			IConfigurationElement aConfigElement,
			INavigatorContentDescriptor aContentDescriptor) {
		element = aConfigElement;
		contentDescriptor = aContentDescriptor;
		init();
	}

	private void init() {
		IConfigurationElement[] children = element.getChildren(TAG_POSSIBLE_DROP_TARGETS);
		if (children.length == 1) {
			dropExpr = new CustomAndExpression(children[0]);
		}
	}

	public boolean isDragElementSupported(Object anElement) { 
		return contentDescriptor.isPossibleChild(anElement); 
	}

	public boolean areDragElementsSupported(IStructuredSelection aSelection) {
		if (aSelection.isEmpty()) {
			return false;
		}
		return contentDescriptor.arePossibleChildren(aSelection);
	}

	public boolean isDropElementSupported(Object anElement) {
		if (dropExpr != null && anElement != null) {
			IEvaluationContext context = NavigatorPlugin.getEvalContext(anElement);
			return NavigatorPlugin.safeEvaluate(dropExpr, context) == EvaluationResult.TRUE;
		}
		return false;
	}

	public CommonDropAdapterAssistant createDropAssistant() {
		final CommonDropAdapterAssistant[] retValue = new CommonDropAdapterAssistant[1];
		SafeRunner.run(new NavigatorSafeRunnable(element) {
			@Override
			public void run() throws Exception {
				retValue[0] = (CommonDropAdapterAssistant) element
						.createExecutableExtension(ATT_CLASS);
			}
		});
		if (retValue[0] != null)
			return retValue[0];
		return SkeletonCommonDropAssistant.INSTANCE;
	}

	public INavigatorContentDescriptor getContentDescriptor() {
		return contentDescriptor;
	}

}
