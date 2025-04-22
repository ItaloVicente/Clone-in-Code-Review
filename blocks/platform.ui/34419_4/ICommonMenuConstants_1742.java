package org.eclipse.ui.navigator;

import org.eclipse.jface.viewers.ILabelProvider;

public interface ICommonLabelProvider extends ILabelProvider, IMementoAware,
		IDescriptionProvider {

	void init(ICommonContentExtensionSite aConfig);

}
