package org.eclipse.ui.navigator;

import org.eclipse.jface.viewers.ITreeContentProvider;

public interface ICommonContentProvider extends ITreeContentProvider,
		IMementoAware {

	void init(ICommonContentExtensionSite aConfig);

}
