package org.eclipse.ui.navigator;

import java.util.Set;

import org.eclipse.jface.viewers.IStructuredSelection;

public interface INavigatorContentDescriptor {

	String getId();

	String getName();

	int getPriority();
	
	public String getAppearsBeforeId();
	
	public int getSequenceNumber();
	
	boolean isActiveByDefault();

	boolean isSortOnly();
	
	boolean isTriggerPoint(Object anElement);

	boolean isPossibleChild(Object anElement);

	boolean arePossibleChildren(IStructuredSelection aSelection);

	String getSuppressedExtensionId();

	OverridePolicy getOverridePolicy();

	INavigatorContentDescriptor getOverriddenDescriptor();

	boolean hasOverridingExtensions();

	Set getOverriddingExtensions();

	boolean hasSaveablesProvider();

}
