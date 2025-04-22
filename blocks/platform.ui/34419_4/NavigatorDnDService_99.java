
package org.eclipse.ui.internal.navigator.dnd;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.ui.navigator.INavigatorContentService;

public class NavigatorContentServiceTransfer {
	
	private static final NavigatorContentServiceTransfer instance = new NavigatorContentServiceTransfer(); 
	
	public static NavigatorContentServiceTransfer getInstance() {
		return instance;
	}

	private final Set<WeakReference<INavigatorContentService>> registeredContentServices = new HashSet<WeakReference<INavigatorContentService>>();
	
	public synchronized void registerContentService(INavigatorContentService aContentService) { 
		if(findService(aContentService.getViewerId()) == null) {
			registeredContentServices.add(new WeakReference<INavigatorContentService>(aContentService));
		}
	}
	
	public synchronized void unregisterContentService(INavigatorContentService aContentService) { 
  
		for (Iterator<WeakReference<INavigatorContentService>> iter = registeredContentServices.iterator(); iter.hasNext();) {
			WeakReference ref = iter.next();
			if(ref.get() == null) {
				iter.remove();
			} else { 
				if(ref.get() == aContentService) {
					iter.remove(); 
					return;
				}
			}
		} 
	}
	
	public synchronized INavigatorContentService findService(String aViewerId) {
		if(aViewerId == null || aViewerId.length() == 0) {
			return null;
		}
		INavigatorContentService contentService;
		for (Iterator<WeakReference<INavigatorContentService>> iter = registeredContentServices.iterator(); iter.hasNext();) {
			WeakReference ref = iter.next();
			if(ref.get() == null) {
				iter.remove();
			} else {
				contentService = (INavigatorContentService)ref.get();
				if(aViewerId.equals(contentService.getViewerId())) {
					return contentService;
				} 
			}
		}
		return null;
	} 
	

}
