package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public final  class SkeletonActionProvider extends CommonActionProvider {

	public static final CommonActionProvider INSTANCE = new SkeletonActionProvider();
 
	private SkeletonActionProvider() {
		super();
	}

	@Override
	public void init(ICommonActionExtensionSite aConfig) {

	}
}
