
package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.navigator.CommonDragAdapterAssistant;

public final class CommonDragAssistantDescriptor implements IViewerExtPtConstants {

	private IConfigurationElement element;

	
		element = aConfigElement;
	}

	public CommonDragAdapterAssistant createDragAssistant() {

		final CommonDragAdapterAssistant[] da = new CommonDragAdapterAssistant[1];

		SafeRunner.run(new NavigatorSafeRunnable(element) {
			@Override
			public void run() throws Exception {
				da[0] = (CommonDragAdapterAssistant) element.createExecutableExtension(ATT_CLASS);
			}
		});
		if (da[0] != null)
			return da[0];
		return SkeletonCommonDragAssistant.INSTANCE;

	}

}
