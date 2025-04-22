
package org.eclipse.ui.internal.navigator.sorters;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.ui.internal.navigator.CustomAndExpression;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.internal.navigator.extensions.INavigatorContentExtPtConstants;

public class CommonSorterDescriptor implements INavigatorContentExtPtConstants {

	private IConfigurationElement element;

	private Expression parentExpression;

	private String id;

	protected CommonSorterDescriptor(IConfigurationElement anElement) { 
		element = anElement;
		init();
	}

	private void init() {
		id = element.getAttribute(ATT_ID);
		if (id == null) {
			id = ""; //$NON-NLS-1$
		}
		IConfigurationElement[] children = element
				.getChildren(TAG_PARENT_EXPRESSION);
		if (children.length == 1) {
			parentExpression = new CustomAndExpression(children[0]);
		}
	}

	public String getId() {
		return id;
	}
 
	public boolean isEnabledForParent(Object aParent) {
		if(aParent == null) {
			return false;
		}

		if (parentExpression != null) {
			IEvaluationContext context = NavigatorPlugin.getEvalContext(aParent);
			return NavigatorPlugin.safeEvaluate(parentExpression, context) == EvaluationResult.TRUE;
		}
		return true;
	}

	public ViewerSorter createSorter() {
		final ViewerSorter[] sorter = new ViewerSorter[1];

		SafeRunner.run(new NavigatorSafeRunnable(element) {
			@Override
			public void run() throws Exception {
				sorter[0] = (ViewerSorter) element.createExecutableExtension(ATT_CLASS);
			}
		});
		if (sorter[0] != null)
			return sorter[0];
		return SkeletonViewerSorter.INSTANCE;
	}

	@Override
	public String toString() {
		return "CommonSorterDescriptor[" + getId() + "]"; //$NON-NLS-1$//$NON-NLS-2$
	}
}
