package org.eclipse.ui.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public interface INavigatorContentExtension extends IAdaptable {

	String getId();

	INavigatorContentDescriptor getDescriptor();

	ITreeContentProvider getContentProvider();

	ICommonLabelProvider getLabelProvider(); 

	boolean isLoaded();

	IExtensionStateModel getStateModel();

}
