package org.eclipse.ui.navigator;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.internal.navigator.NavigatorContentService;


public final class NavigatorContentServiceFactory {
	
	public static final NavigatorContentServiceFactory INSTANCE = new NavigatorContentServiceFactory(); 
	
	
	public INavigatorContentService createContentService(String aViewerId) { 
		return createContentService(aViewerId, null);
	}
	
	public INavigatorContentService createContentService(String aViewerId, StructuredViewer aViewer) {
		if(aViewer == null) {
			return new NavigatorContentService(aViewerId);
		}
		return new NavigatorContentService(aViewerId, aViewer);
	}

}
