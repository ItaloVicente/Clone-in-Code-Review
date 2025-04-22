
package org.eclipse.ui.internal.navigator.filters;

import org.eclipse.core.expressions.Expression;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.internal.navigator.CustomAndExpression;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.internal.navigator.extensions.INavigatorContentExtPtConstants;
import org.eclipse.ui.navigator.ICommonFilterDescriptor;

public class CommonFilterDescriptor implements ICommonFilterDescriptor,
		INavigatorContentExtPtConstants {

	private IConfigurationElement element;

	private Expression filterExpression;

	private String id;
	
	protected CommonFilterDescriptor(IConfigurationElement anElement) {

		element = anElement;
		init();
	}

	private void init() {
		id = element.getAttribute(ATT_ID);
		if (id == null) {
			id = ""; //$NON-NLS-1$
		}
		IConfigurationElement[] children = element
				.getChildren(TAG_FILTER_EXPRESSION);
		if (children.length == 1) {
			filterExpression = new CustomAndExpression(children[0]);
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return element.getAttribute(ATT_NAME);
	}

	@Override
	public String getDescription() {
		return element.getAttribute(ATT_DESCRIPTION);
	}

	@Override
	public boolean isActiveByDefault() {
		return Boolean.valueOf(element.getAttribute(ATT_ACTIVE_BY_DEFAULT))
				.booleanValue();
	}

	public boolean isVisibleInUi() {
		String attr = element.getAttribute(ATT_VISIBLE_IN_UI);
		if (attr == null)
			return true;
		return Boolean.valueOf(attr).booleanValue();
	}

	public ViewerFilter createFilter() {
		final ViewerFilter[] filter = new ViewerFilter[1];

		SafeRunner.run(new NavigatorSafeRunnable() {
			@Override
			public void run() throws Exception {
				if (filterExpression != null) {
					if (element.getAttribute(ATT_CLASS) != null) {
						NavigatorPlugin
								.log(
										IStatus.WARNING,
										0,
										"A \"commonFilter\" was specified in " + //$NON-NLS-1$
												element.getDeclaringExtension()
														.getNamespaceIdentifier()
												+ " which specifies a \"class\" attribute and an Core Expression.\n" + //$NON-NLS-1$
												"Only the Core Expression will be respected.", //$NON-NLS-1$
										null);
					}

					filter[0] = new CoreExpressionFilter(filterExpression);
					return;
				}
				filter[0] = (ViewerFilter) element.createExecutableExtension(ATT_CLASS);
			}
		});

		if (filter[0] != null)
			return filter[0];
		return SkeletonViewerFilter.INSTANCE;
	}

	@Override
	public String toString() {
		return "CommonFilterDescriptor[" + getName() + " (" + getId() + ")]"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}
}
