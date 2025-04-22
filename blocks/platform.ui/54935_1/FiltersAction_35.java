package org.eclipse.ui.views.markers.internal;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

class FilterEnablementAction extends Action {

	private MarkerFilter markerFilter;
	private MarkerView markerView;

	public FilterEnablementAction(MarkerFilter filter, MarkerView view) {
		super(filter.getName(),SWT.CHECK);
		setChecked(filter.isEnabled());
		markerFilter = filter;
		markerView = view;
		
	}
	
	@Override
	public void run() {
		markerFilter.setEnabled(!markerFilter.isEnabled());
		setChecked(markerFilter.isEnabled());
		markerView.updateForFilterChanges();
	}


}
