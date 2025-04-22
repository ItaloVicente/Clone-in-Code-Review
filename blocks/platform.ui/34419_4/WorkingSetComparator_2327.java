package org.eclipse.ui.internal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.misc.Policy;
import org.eclipse.ui.internal.registry.WorkingSetDescriptor;
import org.eclipse.ui.internal.registry.WorkingSetRegistry;
import org.eclipse.ui.internal.util.Util;

public class WorkingSet extends AbstractWorkingSet {
	private static final String DEFAULT_ID = "org.eclipse.ui.resourceWorkingSetPage"; //$NON-NLS-1$
	
	private String editPageId;

	public WorkingSet(String name, String label, IAdaptable[] elements) {
		super(name, label);
		internalSetElements(elements);
	}

	protected WorkingSet(String name, String label, IMemento memento) {
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

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object instanceof WorkingSet) {
			WorkingSet workingSet = (WorkingSet) object;
			return Util.equals(workingSet.getName(), getName())
					&& Util.equals(workingSet.getElementsArray(),
							getElementsArray())
					&& Util.equals(workingSet.getId(), getId());
		}
		return false;
	}

	@Override
	public boolean isEditable() {
		WorkingSetDescriptor descriptor = getDescriptor(null);
		return descriptor != null && descriptor.isEditable();
	}

	@Override
	public String getId() {
		return editPageId;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		WorkingSetDescriptor descriptor = getDescriptor(DEFAULT_ID);
		if (descriptor == null) {
			return null;
		}
		return descriptor.getIcon();
	}

	@Override
	public int hashCode() {
		int hashCode = getName().hashCode();

		if (editPageId != null) {
			hashCode &= editPageId.hashCode();
		}
		return hashCode;
	}

	@Override
	void restoreWorkingSet() {
		IMemento[] itemMementos = workingSetMemento
				.getChildren(IWorkbenchConstants.TAG_ITEM);
		final Set items = new HashSet();
		for (int i = 0; i < itemMementos.length; i++) {
			final IMemento itemMemento = itemMementos[i];
			final String factoryID = itemMemento
					.getString(IWorkbenchConstants.TAG_FACTORY_ID);

			if (factoryID == null) {
				WorkbenchPlugin
						.log("Unable to restore working set item - no factory ID."); //$NON-NLS-1$
				continue;
			}
			final IElementFactory factory = PlatformUI.getWorkbench()
					.getElementFactory(factoryID);
			if (factory == null) {
				WorkbenchPlugin
						.log("Unable to restore working set item - cannot instantiate factory: " + factoryID); //$NON-NLS-1$
				continue;
			}
			SafeRunner
					.run(new SafeRunnable(
							"Unable to restore working set item - exception while invoking factory: " + factoryID) { //$NON-NLS-1$

						@Override
						public void run() throws Exception {
							IAdaptable item = factory
									.createElement(itemMemento);
							if (item == null) {
								if (Policy.DEBUG_WORKING_SETS)
									WorkbenchPlugin
											.log("Unable to restore working set item - cannot instantiate item: " + factoryID); //$NON-NLS-1$

							} else
								items.add(item);
						}
					});
		}
		internalSetElements((IAdaptable[]) items.toArray(new IAdaptable[items
				.size()]));
	}

	@Override
	public void saveState(IMemento memento) {
		if (workingSetMemento != null) {
			memento.putMemento(workingSetMemento);
		} else {
			memento.putString(IWorkbenchConstants.TAG_NAME, getName());
			memento.putString(IWorkbenchConstants.TAG_LABEL, getLabel());
			memento.putString(IWorkbenchConstants.TAG_ID, getUniqueId());
			memento.putString(IWorkbenchConstants.TAG_EDIT_PAGE_ID, editPageId);
			Iterator iterator = elements.iterator();
			while (iterator.hasNext()) {
				IAdaptable adaptable = (IAdaptable) iterator.next();
				final IPersistableElement persistable = (IPersistableElement) Util
						.getAdapter(adaptable, IPersistableElement.class);
				if (persistable != null) {
					final IMemento itemMemento = memento
							.createChild(IWorkbenchConstants.TAG_ITEM);

					itemMemento.putString(IWorkbenchConstants.TAG_FACTORY_ID,
							persistable.getFactoryId());
					SafeRunner
							.run(new SafeRunnable(
									"Problems occurred while saving persistable item state") { //$NON-NLS-1$

								@Override
								public void run() throws Exception {
									persistable.saveState(itemMemento);
								}
							});
				}
			}
		}
	}

	@Override
	public void setElements(IAdaptable[] newElements) {
		internalSetElements(newElements);
		fireWorkingSetChanged(
				IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE, null);
	}

	@Override
	public void setId(String pageId) {
		editPageId = pageId;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public boolean isSelfUpdating() {
		WorkingSetDescriptor descriptor = getDescriptor(null);
		return descriptor != null && descriptor.getUpdaterClassName() != null;
	}

	@Override
	public boolean isAggregateWorkingSet() {
		return false;
	}

	private WorkingSetDescriptor getDescriptor(String defaultId) {
		WorkingSetRegistry registry = WorkbenchPlugin.getDefault()
				.getWorkingSetRegistry();
		String id = getId();
		if (id == null)
			id = defaultId;
		if (id == null)
			return null;

		return registry.getWorkingSetDescriptor(id);
	}
	
	@Override
	public IAdaptable[] adaptElements(IAdaptable[] objects) {
		IWorkingSetManager manager = getManager();
		if (manager instanceof WorkingSetManager) {
			WorkingSetDescriptor descriptor = getDescriptor(null);
			if (descriptor == null || !descriptor.isElementAdapterClassLoaded()) 
				return objects;
			return ((WorkingSetManager) manager).getElementAdapter(
						descriptor).adaptElements(this, objects);
		}
		return objects;
	}
}
