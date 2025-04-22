
package org.eclipse.ui.internal.navigator;

import java.util.Iterator;
import java.util.LinkedHashSet;

import org.eclipse.ui.navigator.INavigatorContentDescriptor;

public class ContributorTrackingSet extends LinkedHashSet {

	
	private static final long serialVersionUID = 2516241537206281972L;
	
	private INavigatorContentDescriptor contributor;
	private INavigatorContentDescriptor firstClassContributor;
	private NavigatorContentService contentService;
	
	public ContributorTrackingSet(NavigatorContentService aContentService) {
		contentService = aContentService;
	}
	
	public ContributorTrackingSet(NavigatorContentService aContentService, Object[] elements) {
		
		for (int i = 0; i < elements.length; i++) 
			super.add(elements[i]); 
		
		contentService = aContentService;
	}
	
	@Override
	public boolean add(Object o) { 
		if (contributor != null) {
			contentService.rememberContribution(contributor, firstClassContributor, o);
		}
		return super.add(o);
	}
	
	@Override
	public boolean remove(Object o) { 
		contentService.forgetContribution(o);
		return super.remove(o);
	}

	
	@Override
	public void clear() { 
		Iterator it = iterator();
		while (it.hasNext())
			contentService.forgetContribution(it.next());
		super.clear();
	}

	public INavigatorContentDescriptor getContributor() {
		return contributor;
	}

	public INavigatorContentDescriptor getFirstClassContributor() {
		return firstClassContributor;
	}

	public void setContributor(INavigatorContentDescriptor newContributor, INavigatorContentDescriptor theFirstClassContributor) {
		contributor = newContributor;
		firstClassContributor = theFirstClassContributor;
	}

	public void setContents(Object[] contents) {
		super.clear();
		if(contents != null) 
			for (int i = 0; i < contents.length; i++) 
				add(contents[i]); 
		
	}
	
	@Override
	public Iterator iterator() {
		return new Iterator() {

			Iterator delegateIterator = ContributorTrackingSet.super.iterator();
			Object current;

			@Override
			public boolean hasNext() {
				return delegateIterator.hasNext();
			}

			@Override
			public Object next() {
				current = delegateIterator.next();
				return current;
			}

			@Override
			public void remove() {
				delegateIterator.remove();
				contentService.forgetContribution(current);
			}
		};
	}
	
}
