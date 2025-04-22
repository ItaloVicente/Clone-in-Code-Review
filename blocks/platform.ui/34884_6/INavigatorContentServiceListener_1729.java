package org.eclipse.ui.navigator;

import java.util.Set;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerSorter;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISaveablesSource;

public interface INavigatorContentService {

	ITreeContentProvider createCommonContentProvider();

	ILabelProvider createCommonLabelProvider();

	IDescriptionProvider createCommonDescriptionProvider();

	IExtensionStateModel findStateModel(String anExtensionId);

	String getViewerId();

	INavigatorViewerDescriptor getViewerDescriptor();

	boolean isActive(String anExtensionId);

	boolean isVisible(String anExtensionId);

	String[] getVisibleExtensionIds();

	INavigatorContentDescriptor[] getVisibleExtensions();

	INavigatorContentDescriptor[] bindExtensions(String[] extensionIds,
			boolean isRoot);

	void restoreState(IMemento aMemento);

	void saveState(IMemento aMemento);

	void addListener(INavigatorContentServiceListener aListener);

	void removeListener(INavigatorContentServiceListener aListener);

	void update();

	void dispose();

	Set findRootContentExtensions(Object anElement);

	Set findContentExtensionsByTriggerPoint(Object anElement);

	Set findContentExtensionsWithPossibleChild(Object anElement);

	INavigatorFilterService getFilterService();

	INavigatorSorterService getSorterService();

	INavigatorPipelineService getPipelineService();

	INavigatorDnDService getDnDService();

	INavigatorActivationService getActivationService();
	
	INavigatorSaveablesService getSaveablesService();
	
	public INavigatorContentExtension getContentExtensionById(String anExtensionId);
	
	public INavigatorContentDescriptor getContentDescriptorById(String anExtensionId);



}
