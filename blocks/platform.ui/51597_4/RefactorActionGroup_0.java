package org.eclipse.ui.internal.navigator.resources.actions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

public class DelegatingSelectionListenerAction extends BaseSelectionListenerAction {
	private BaseSelectionListenerAction[] possibleDelegates;
	private BaseSelectionListenerAction chosenDelegate;

	private IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			firePropertyChange(event);
		}
	};

	public DelegatingSelectionListenerAction(BaseSelectionListenerAction... actions) {
		super(null);
		this.possibleDelegates = actions;
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		for (BaseSelectionListenerAction action : possibleDelegates) {
			if (chosenDelegate == action) {
				action.selectionChanged(selection);
				if (action.isEnabled()) {
					return true;
				}
				action.removePropertyChangeListener(propertyChangeListener);
				chosenDelegate = null;
			} else {
				final List<PropertyChangeEvent> events = new LinkedList<>();
				IPropertyChangeListener accumulator = new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						events.add(event);
					}
				};
				action.addPropertyChangeListener(accumulator);
				action.selectionChanged(selection);
				action.removePropertyChangeListener(accumulator);
				if (action.isEnabled()) {
					if (chosenDelegate != null) {
						chosenDelegate.removePropertyChangeListener(propertyChangeListener);
					}
					chosenDelegate = action;
					chosenDelegate.addPropertyChangeListener(propertyChangeListener);
					for (PropertyChangeEvent event : events) {
						firePropertyChange(event);
					}
					return true;
				}
			}
		}
		chosenDelegate = null;
		return false;
	}

	@Override
	public String getDescription() {
		if (chosenDelegate != null) {
			return chosenDelegate.getDescription();
		}
		return super.getDescription();
	}

	@Override
	public ImageDescriptor getDisabledImageDescriptor() {
		if (chosenDelegate != null) {
			return chosenDelegate.getDisabledImageDescriptor();
		}
		return super.getDisabledImageDescriptor();
	}

	@Override
	public ImageDescriptor getHoverImageDescriptor() {
		if (chosenDelegate != null) {
		return chosenDelegate.getHoverImageDescriptor();
		}
		return super.getHoverImageDescriptor();
	}

	@Override
	public String getId() {
		if (chosenDelegate != null) {
		return chosenDelegate.getId();
		}
		return super.getId();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		if (chosenDelegate != null) {
			return chosenDelegate.getImageDescriptor();
		}
		return super.getImageDescriptor();
	}

	@Override
	public int getStyle() {
		if (chosenDelegate != null) {
			return chosenDelegate.getStyle();
		}
		return super.getStyle();
	}

	@Override
	public String getText() {
		if (chosenDelegate != null) {
			return chosenDelegate.getText();
		}
		return super.getText();
	}

	@Override
	public String getToolTipText() {
		if (chosenDelegate != null) {
			return chosenDelegate.getToolTipText();
		}
		return super.getToolTipText();
	}

	@Override
	public boolean isEnabled() {
		return chosenDelegate != null;
	}

	@Override
	public boolean isHandled() {
		if (chosenDelegate != null) {
			return chosenDelegate.isHandled();
		}
		return false;
	}

	@Override
	public void runWithEvent(Event event) {
		if (chosenDelegate != null) {
			chosenDelegate.runWithEvent(event);
		}
	}

	@Override
	public void run() {
		if (chosenDelegate != null) {
			chosenDelegate.run();
		}
	}

}
