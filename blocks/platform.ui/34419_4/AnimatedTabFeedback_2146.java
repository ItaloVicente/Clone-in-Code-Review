
package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IAggregateWorkingSet;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.internal.util.Util;

public class AggregateWorkingSet extends AbstractWorkingSet implements
		IAggregateWorkingSet, IPropertyChangeListener {

	private IWorkingSet[] components;

	private boolean inElementConstruction = false;

	public AggregateWorkingSet(String name, String label,
			IWorkingSet[] components) {
		super(name, label);

		IWorkingSet[] componentCopy = new IWorkingSet[components.length];
		System.arraycopy(components, 0, componentCopy, 0, components.length);
		internalSetComponents(componentCopy);
		constructElements(false);
	}

	public AggregateWorkingSet(String name, String label, IMemento memento) {
		super(name, label);
		workingSetMemento = memento;
		if (workingSetMemento != null) {
			String uniqueId = workingSetMemento
					.getString(IWorkbenchConstants.TAG_ID);
			if (uniqueId != null) {
				setUniqueId(uniqueId);
			}
		}
	}

	void setComponents(IWorkingSet[] components) {
		internalSetComponents(components);
		constructElements(true);
	}

	private void internalSetComponents(IWorkingSet[] components) {
		this.components = components;
	}

	private void constructElements(boolean fireEvent) {
		if (inElementConstruction) {
			String msg = NLS.bind(WorkbenchMessages.ProblemCyclicDependency, getName());
            WorkbenchPlugin.log(msg);
            throw new IllegalStateException(msg);
		}
		inElementConstruction = true;
		try {
			Set elements = new HashSet();
			IWorkingSet[] localComponents = getComponentsInternal();
			for (int i = 0; i < localComponents.length; i++) {
				IWorkingSet workingSet = localComponents[i];
				try {
					IAdaptable[] componentElements = workingSet.getElements();
					elements.addAll(Arrays.asList(componentElements));
				} catch (IllegalStateException e) { // an invalid component; remove it
					IWorkingSet[] tmp = new IWorkingSet[components.length - 1];
					if (i > 0)
						System.arraycopy(components, 0, tmp, 0, i);
					if (components.length - i - 1 > 0)
						System.arraycopy(components, i + 1, tmp, i, components.length - i - 1);
					components = tmp;
					workingSetMemento = null; // toss cached info
					fireWorkingSetChanged(IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE, null);						
					continue;
				}
			}
			internalSetElements((IAdaptable[]) elements
					.toArray(new IAdaptable[elements.size()]));
			if (fireEvent) {
				fireWorkingSetChanged(
					IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE, null);
			}
		} finally {
			inElementConstruction = false;
		}
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return WorkbenchImages
				.getImageDescriptor(IWorkbenchGraphicConstants.IMG_OBJ_WORKING_SETS);
	}

	@Override
	public void setElements(IAdaptable[] elements) {
	}

	@Override
	public void setId(String id) {

	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public boolean isVisible() {
		return false;
	}

	@Override
	public void saveState(IMemento memento) {
		if (workingSetMemento != null) {
			memento.putMemento(workingSetMemento);
		} else {
			memento.putString(IWorkbenchConstants.TAG_NAME, getName());
			memento.putString(IWorkbenchConstants.TAG_LABEL, getLabel());
			memento.putString(IWorkbenchConstants.TAG_ID, getUniqueId());
			memento.putString(AbstractWorkingSet.TAG_AGGREGATE, Boolean.TRUE
					.toString());

			IWorkingSet[] localComponents = getComponentsInternal();
			for (int i = 0; i < localComponents.length; i++) {
				IWorkingSet componentSet = localComponents[i];
				memento.createChild(IWorkbenchConstants.TAG_WORKING_SET,
						componentSet.getName());
			}
		}
	}

	@Override
	public void connect(IWorkingSetManager manager) {
		manager.addPropertyChangeListener(this);
		super.connect(manager);
	}

	@Override
	public void disconnect() {
		IWorkingSetManager connectedManager = getManager();
		if (connectedManager != null)
			connectedManager.removePropertyChangeListener(this);
		super.disconnect();
	}

	@Override
	public IWorkingSet[] getComponents() {
		IWorkingSet[] localComponents = getComponentsInternal();
		IWorkingSet[] copiedArray = new IWorkingSet[localComponents.length];
		System.arraycopy(localComponents, 0, copiedArray, 0, localComponents.length);
		return copiedArray;
	}

	private IWorkingSet[] getComponentsInternal() {
		if (components == null) {
			restoreWorkingSet();
			workingSetMemento = null;
		}
		return components;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();
		if (property.equals(IWorkingSetManager.CHANGE_WORKING_SET_REMOVE)) {
			IWorkingSet[] localComponents = getComponentsInternal();
			for (int i = 0; i < localComponents.length; i++) {
				IWorkingSet set = localComponents[i];
				if (set.equals(event.getOldValue())) {
					IWorkingSet[] newComponents = new IWorkingSet[localComponents.length - 1];
					Util
							.arrayCopyWithRemoval(localComponents,
									newComponents, i);
					setComponents(newComponents);
				}
			}
		} else if (property
				.equals(IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE)) {
			IWorkingSet[] localComponents = getComponentsInternal();
			for (int i = 0; i < localComponents.length; i++) {
				IWorkingSet set = localComponents[i];
				if (set.equals(event.getNewValue())) {
					constructElements(true);
					break;
				}
			}
		}
	}

	@Override
	void restoreWorkingSet() {
		IWorkingSetManager manager = getManager();
		if (manager == null) {
			throw new IllegalStateException();
		}
		IMemento[] workingSetReferences = workingSetMemento
				.getChildren(IWorkbenchConstants.TAG_WORKING_SET);
		ArrayList list = new ArrayList(workingSetReferences.length);

		for (int i = 0; i < workingSetReferences.length; i++) {
			IMemento setReference = workingSetReferences[i];
			String setId = setReference.getID();
			IWorkingSet set = manager.getWorkingSet(setId);
			if (set != null) {
				list.add(set);
			}
		}
		internalSetComponents((IWorkingSet[]) list
				.toArray(new IWorkingSet[list.size()]));
		constructElements(false);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object instanceof AggregateWorkingSet) {
			AggregateWorkingSet workingSet = (AggregateWorkingSet) object;

			return Util.equals(workingSet.getName(), getName())
					&& Util.equals(workingSet.getComponentsInternal(), getComponentsInternal());
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = getName().hashCode() & getComponentsInternal().hashCode();
		return hashCode;
	}
	
	@Override
	public boolean isSelfUpdating() {
		IWorkingSet[] localComponents = getComponentsInternal();
		if (localComponents == null || localComponents.length == 0) {
			return false;
		}
		for (int i= 0; i < localComponents.length; i++) {
			if (!localComponents[i].isSelfUpdating()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean isAggregateWorkingSet() {
		return true;
	}

	@Override
	public IAdaptable[] adaptElements(IAdaptable[] objects) {
		return new IAdaptable[0];
	}
}
