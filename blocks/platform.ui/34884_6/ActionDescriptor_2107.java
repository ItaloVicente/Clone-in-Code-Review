package org.eclipse.ui.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetElementAdapter;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.IWorkingSetUpdater;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.dialogs.IWorkingSetEditWizard;
import org.eclipse.ui.dialogs.IWorkingSetNewWizard;
import org.eclipse.ui.dialogs.IWorkingSetPage;
import org.eclipse.ui.dialogs.IWorkingSetSelectionDialog;
import org.eclipse.ui.internal.dialogs.WorkingSetEditWizard;
import org.eclipse.ui.internal.dialogs.WorkingSetNewWizard;
import org.eclipse.ui.internal.dialogs.WorkingSetSelectionDialog;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.WorkingSetDescriptor;
import org.eclipse.ui.internal.registry.WorkingSetRegistry;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.progress.WorkbenchJob;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;


public abstract class AbstractWorkingSetManager extends EventManager implements
		IWorkingSetManager, BundleListener, IExtensionChangeHandler {
	
	static abstract class WorkingSetRunnable implements ISafeRunnable {

		@Override
		public void handleException(Throwable exception) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(PlatformUI.PLUGIN_ID, exception));
		}
	}

	private SortedSet workingSets = new TreeSet(new Comparator() {
		@Override
		public int compare(Object o1, Object o2) {
			return ((AbstractWorkingSet) o1).getUniqueId().compareTo(
					((AbstractWorkingSet) o2).getUniqueId());
		}
	});
    
    private List recentWorkingSets = new ArrayList();

    private BundleContext bundleContext;
    private Map/*<String, IWorkingSetUpdater>*/ updaters= new HashMap();

	private Map/*<String, IWorkingSetElementAdapter>*/ elementAdapters = new HashMap();
    
    private static final IWorkingSetUpdater NULL_UPDATER= new IWorkingSetUpdater() {
		@Override
		public void add(IWorkingSet workingSet) {
		}
		@Override
		public boolean remove(IWorkingSet workingSet) {
			return true;
		}
		@Override
		public boolean contains(IWorkingSet workingSet) {
			return true;
		}
		@Override
		public void dispose() {
		}
	};
	
	private static final IWorkingSetElementAdapter IDENTITY_ADAPTER = new IWorkingSetElementAdapter() {

		@Override
		public IAdaptable[] adaptElements(IWorkingSet ws, IAdaptable[] elements) {
			return elements;
		}

		@Override
		public void dispose() {
		}
	};
		
    private static WorkingSetDescriptor[] getSupportedEditableDescriptors(
            String[] supportedWorkingSetIds) {
        WorkingSetRegistry registry = WorkbenchPlugin.getDefault()
                .getWorkingSetRegistry();
        if (supportedWorkingSetIds == null) {
            return registry.getNewPageWorkingSetDescriptors();
        }
        List result = new ArrayList(supportedWorkingSetIds.length);
        for (int i = 0; i < supportedWorkingSetIds.length; i++) {
            WorkingSetDescriptor desc = registry
                    .getWorkingSetDescriptor(supportedWorkingSetIds[i]);
            if (desc != null && desc.isEditable()) {
                result.add(desc);
            }
        }
        return (WorkingSetDescriptor[]) result
                .toArray(new WorkingSetDescriptor[result.size()]);
    }
    
    protected AbstractWorkingSetManager(BundleContext context) {
    	bundleContext= context;
    	bundleContext.addBundleListener(this);
    	PlatformUI.getWorkbench().getExtensionTracker().registerHandler(this, ExtensionTracker
				.createExtensionPointFilter(getExtensionPointFilter()));
	}

	private IExtensionPoint getExtensionPointFilter() {
		return Platform.getExtensionRegistry().getExtensionPoint(
				PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_WORKINGSETS);
	}
    
    @Override
	public void dispose() {
    	bundleContext.removeBundleListener(this);
    	for (final Iterator iter= updaters.values().iterator(); iter.hasNext();) {
			SafeRunner.run(new WorkingSetRunnable() {

				@Override
				public void run() throws Exception {
					((IWorkingSetUpdater) iter.next()).dispose();
				}
			});
		}
    	
    	for (final Iterator iter= elementAdapters.values().iterator(); iter.hasNext();) {
			SafeRunner.run(new WorkingSetRunnable() {

				@Override
				public void run() throws Exception {
					((IWorkingSetElementAdapter)iter.next()).dispose();
				}
			});
		}
    }
    
    
    @Override
	public IWorkingSet createWorkingSet(String name, IAdaptable[] elements) {
        return new WorkingSet(name, name, elements);
    }
    
    @Override
	public IWorkingSet createAggregateWorkingSet(String name, String label,
			IWorkingSet[] components) {
		return new AggregateWorkingSet(name, label, components);
	}

    @Override
	public IWorkingSet createWorkingSet(IMemento memento) {
        return restoreWorkingSet(memento);
    }

    
    @Override
	public void addWorkingSet(IWorkingSet workingSet) {
    	IWorkingSet wSet=getWorkingSet(workingSet.getName());
    	Assert.isTrue(wSet==null,"working set with same name already registered"); //$NON-NLS-1$
        internalAddWorkingSet(workingSet);
    }

    private void internalAddWorkingSet(IWorkingSet workingSet) {
		workingSets.add(workingSet);
        ((AbstractWorkingSet)workingSet).connect(this);
        addToUpdater(workingSet);
        firePropertyChange(CHANGE_WORKING_SET_ADD, null, workingSet);
	}

    protected boolean internalRemoveWorkingSet(IWorkingSet workingSet) {
        boolean workingSetRemoved = workingSets.remove(workingSet);
        boolean recentWorkingSetRemoved = recentWorkingSets.remove(workingSet);
        
        if (workingSetRemoved) {
        	((AbstractWorkingSet)workingSet).disconnect();
        	removeFromUpdater(workingSet);
            firePropertyChange(CHANGE_WORKING_SET_REMOVE, workingSet, null);
        }
        return workingSetRemoved || recentWorkingSetRemoved;
    }

    @Override
	public IWorkingSet[] getWorkingSets() {
		SortedSet visibleSubset = new TreeSet(WorkingSetComparator
				.getInstance());
    		for (Iterator i = workingSets.iterator(); i.hasNext();) {
				IWorkingSet workingSet = (IWorkingSet) i.next();
				if (workingSet.isVisible()) {
					visibleSubset.add(workingSet);
				}
			}
        return (IWorkingSet[]) visibleSubset.toArray(new IWorkingSet[visibleSubset.size()]);
    }
    
	@Override
	public IWorkingSet[] getAllWorkingSets() {
		IWorkingSet[] sets = (IWorkingSet[]) workingSets
					.toArray(new IWorkingSet[workingSets.size()]);
		Arrays.sort(sets, WorkingSetComparator.getInstance());
		return sets;
	}

    @Override
	public IWorkingSet getWorkingSet(String name) {
        if (name == null || workingSets == null) {
			return null;
		}

        Iterator iter = workingSets.iterator();
        while (iter.hasNext()) {
            IWorkingSet workingSet = (IWorkingSet) iter.next();
            if (name.equals(workingSet.getName())) {
				return workingSet;
			}
        }
        return null;
    }
    
    
    @Override
	public IWorkingSet[] getRecentWorkingSets() {
        return (IWorkingSet[]) recentWorkingSets.toArray(new IWorkingSet[recentWorkingSets.size()]);
    }

    protected void internalAddRecentWorkingSet(IWorkingSet workingSet) {
    		if (!workingSet.isVisible()) {
				return;
			}
        recentWorkingSets.remove(workingSet);
        recentWorkingSets.add(0, workingSet);
		sizeRecentWorkingSets();
    }

    
    @Override
	public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!getClass().getName().equals(object.getClass().getName())) {
			return false;
		}
        AbstractWorkingSetManager other= (AbstractWorkingSetManager)object;
        return other.workingSets.equals(workingSets);
    }

    @Override
	public int hashCode() {
        return workingSets.hashCode();
    }

    
    @Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
        addListenerObject(listener);
    }

    @Override
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
        removeListenerObject(listener);
    }

    protected void firePropertyChange(String changeId, Object oldValue,
            Object newValue) {
        final Object[] listeners = getListeners();
		
        if (listeners.length == 0) {
			return;
		}
		
        final PropertyChangeEvent event = new PropertyChangeEvent(this,
                changeId, oldValue, newValue);
		Runnable notifier = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < listeners.length; i++) {
					final IPropertyChangeListener listener = (IPropertyChangeListener) listeners[i];
					ISafeRunnable safetyWrapper = new ISafeRunnable() {

						@Override
						public void run() throws Exception {
							listener.propertyChange(event);
						}

						@Override
						public void handleException(Throwable exception) {
						}
					};
					SafeRunner.run(safetyWrapper);
				}
			}
		};
		if (Display.getCurrent() != null) {
			notifier.run();
		} else {
			Display.getDefault().asyncExec(notifier);
		}
    }
    
	public void workingSetChanged(IWorkingSet changedWorkingSet,
			String propertyChangeId, Object oldValue) {
		firePropertyChange(propertyChangeId, oldValue, changedWorkingSet);
	}
    
    
    public void saveWorkingSetState(IMemento memento) {
        Iterator iterator = workingSets.iterator();
        
        
        ArrayList standardSets = new ArrayList();
        ArrayList aggregateSets = new ArrayList();
        while (iterator.hasNext()) {
        		IWorkingSet set = (IWorkingSet) iterator.next();
        		if (set instanceof AggregateWorkingSet) {
					aggregateSets.add(set);
				} else {
					standardSets.add(set);
				}
        }

        saveWorkingSetState(memento, standardSets);
        saveWorkingSetState(memento, aggregateSets);
    }

	private void saveWorkingSetState(final IMemento memento, List list) {
		for (Iterator i = list.iterator(); i.hasNext();) {
            final IPersistableElement persistable = (IWorkingSet) i.next();
			SafeRunner.run(new WorkingSetRunnable() {

				@Override
				public void run() throws Exception {
					XMLMemento dummy = XMLMemento.createWriteRoot(IWorkbenchConstants.TAG_WORKING_SET);
					dummy.putString(IWorkbenchConstants.TAG_FACTORY_ID,
							persistable.getFactoryId());
					persistable.saveState(dummy);
					
					IMemento workingSetMemento = memento
							.createChild(IWorkbenchConstants.TAG_WORKING_SET);
					workingSetMemento.putMemento(dummy);
				}
			});
			
        }
	}
    
    protected void restoreWorkingSetState(IMemento memento) {
        IMemento[] children = memento
                .getChildren(IWorkbenchConstants.TAG_WORKING_SET);
        for (int i = 0; i < children.length; i++) {
            IWorkingSet workingSet = restoreWorkingSet(children[i]);
            if (workingSet != null) {
            	internalAddWorkingSet(workingSet);
            }
        }
    }
    
    protected IWorkingSet restoreWorkingSet(final IMemento memento) {
        String factoryID = memento
                .getString(IWorkbenchConstants.TAG_FACTORY_ID);

        if (factoryID == null) {
            factoryID = AbstractWorkingSet.FACTORY_ID;
        }
        final IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(
                factoryID);
        if (factory == null) {
            WorkbenchPlugin
                    .log("Unable to restore working set - cannot instantiate factory: " + factoryID); //$NON-NLS-1$
            return null;
        }
		final IAdaptable[] adaptable = new IAdaptable[1];
		SafeRunner.run(new WorkingSetRunnable() {

			@Override
			public void run() throws Exception {
				adaptable[0] = factory.createElement(memento);
			}
		});
        if (adaptable[0] == null) {
            WorkbenchPlugin
                    .log("Unable to restore working set - cannot instantiate working set: " + factoryID); //$NON-NLS-1$
            return null;
        }
        if ((adaptable[0] instanceof IWorkingSet) == false) {
            WorkbenchPlugin
                    .log("Unable to restore working set - element is not an IWorkingSet: " + factoryID); //$NON-NLS-1$
            return null;
        }
        return (IWorkingSet) adaptable[0];
    }

    protected void saveMruList(IMemento memento) {
        Iterator iterator = recentWorkingSets.iterator();

        while (iterator.hasNext()) {
            IWorkingSet workingSet = (IWorkingSet) iterator.next();
            IMemento mruMemento = memento
                    .createChild(IWorkbenchConstants.TAG_MRU_LIST);

            mruMemento.putString(IWorkbenchConstants.TAG_NAME, workingSet
                    .getName());
        }
    }

    protected void restoreMruList(IMemento memento) {
        IMemento[] mruWorkingSets = memento
                .getChildren(IWorkbenchConstants.TAG_MRU_LIST);

        for (int i = mruWorkingSets.length - 1; i >= 0; i--) {
            String workingSetName = mruWorkingSets[i]
                    .getString(IWorkbenchConstants.TAG_NAME);
            if (workingSetName != null) {
                IWorkingSet workingSet = getWorkingSet(workingSetName);
                if (workingSet != null) {
                    internalAddRecentWorkingSet(workingSet);
                }
            }
        }
    }

    
    @Override
	public IWorkingSetEditWizard createWorkingSetEditWizard(
            IWorkingSet workingSet) {
        String editPageId = workingSet.getId();
        WorkingSetRegistry registry = WorkbenchPlugin.getDefault()
                .getWorkingSetRegistry();
        IWorkingSetPage editPage = null;

        if (editPageId != null) {
            editPage = registry.getWorkingSetPage(editPageId);
        }
 
		
	     if (editPage == null) {
			editPage = registry.getDefaultWorkingSetPage();
			if (editPage == null) {
				return null;
			}
		}
		 
        WorkingSetEditWizard editWizard = new WorkingSetEditWizard(editPage);
        editWizard.setSelection(workingSet);
        return editWizard;
    }

    @Deprecated
	@Override
	public IWorkingSetSelectionDialog createWorkingSetSelectionDialog(
            Shell parent) {
        return createWorkingSetSelectionDialog(parent, true);
    }

    @Override
	public IWorkingSetSelectionDialog createWorkingSetSelectionDialog(
            Shell parent, boolean multi) {
        return createWorkingSetSelectionDialog(parent, multi, null);
    }
    
    @Override
	public IWorkingSetNewWizard createWorkingSetNewWizard(String[] workingSetIds) {
         WorkingSetDescriptor[] descriptors= getSupportedEditableDescriptors(workingSetIds);
         if (descriptors.length == 0) {
			return null;
		}
         return new WorkingSetNewWizard(descriptors);
}

    
	@Override
	public void bundleChanged(BundleEvent event) {
		String symbolicName = event.getBundle().getSymbolicName();
		if (symbolicName == null)
			return;
		if (!PlatformUI.isWorkbenchRunning()) {
			return;
		}
		
		if (event.getBundle().getState() == Bundle.ACTIVE) {
			final WorkingSetDescriptor[] descriptors = WorkbenchPlugin.getDefault()
					.getWorkingSetRegistry().getUpdaterDescriptorsForNamespace(
							symbolicName);
			
			Job job = new WorkbenchJob(
					NLS
							.bind(
									WorkbenchMessages.AbstractWorkingSetManager_updatersActivating,
									symbolicName)) {

				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					synchronized (updaters) {
						for (int i = 0; i < descriptors.length; i++) {
							WorkingSetDescriptor descriptor = descriptors[i];
							List workingSets = getWorkingSetsForId(descriptor
									.getId());
							if (workingSets.size() == 0) {
								continue;
							}
							final IWorkingSetUpdater updater = getUpdater(descriptor);
							for (Iterator iter = workingSets.iterator(); iter
									.hasNext();) {
								final IWorkingSet workingSet = (IWorkingSet) iter
										.next();
								SafeRunner.run(new WorkingSetRunnable() {

									@Override
									public void run() throws Exception {
										if (!updater.contains(workingSet)) {
											updater.add(workingSet);
										}
									}
								});
							}
						}
					}
					return Status.OK_STATUS;
				}
			};
			job.setSystem(true);
			job.schedule();
		}
	}

	private List getWorkingSetsForId(String id) {
		List result= new ArrayList();
    	for (Iterator iter= workingSets.iterator(); iter.hasNext();) {
    		IWorkingSet ws= (IWorkingSet)iter.next();
    		if (id.equals(ws.getId())) {
				result.add(ws);
			}
		}
    	return result;
	}
	
    private void addToUpdater(final IWorkingSet workingSet) {
    	WorkingSetDescriptor descriptor= WorkbenchPlugin.getDefault()
			.getWorkingSetRegistry().getWorkingSetDescriptor(workingSet.getId());
    	if (descriptor == null || !descriptor.isUpdaterClassLoaded()) {
			return;
		}
		synchronized(updaters) {
	    	final IWorkingSetUpdater updater= getUpdater(descriptor);
	    	SafeRunner.run(new WorkingSetRunnable() {

				@Override
				public void run() throws Exception {
					if (!updater.contains(workingSet)) {
						updater.add(workingSet);
					}
				}});
		}
    }
    
    private IWorkingSetUpdater getUpdater(WorkingSetDescriptor descriptor) {
		IWorkingSetUpdater updater= (IWorkingSetUpdater)updaters.get(descriptor.getId());
    	if (updater == null) {
    		updater= descriptor.createWorkingSetUpdater();
    		if (updater == null) {
    			updater= NULL_UPDATER;
    		} else {
    			firePropertyChange(CHANGE_WORKING_SET_UPDATER_INSTALLED, null, updater);
    			PlatformUI.getWorkbench().getExtensionTracker().registerObject(
						descriptor.getConfigurationElement()
								.getDeclaringExtension(), updater,
						IExtensionTracker.REF_WEAK);
    			
    		}
        	updaters.put(descriptor.getId(), updater);
    	}
		return updater;
	}
    
    IWorkingSetElementAdapter getElementAdapter(WorkingSetDescriptor descriptor) {
		IWorkingSetElementAdapter elementAdapter = (IWorkingSetElementAdapter) elementAdapters
				.get(descriptor.getId());
		if (elementAdapter == null) {
			elementAdapter = descriptor.createWorkingSetElementAdapter();
			if (elementAdapter == null) {
				elementAdapter = IDENTITY_ADAPTER;
			} else {
				elementAdapters.put(descriptor.getId(), elementAdapter);
			}
		}
		return elementAdapter;
	}

	private void removeFromUpdater(final IWorkingSet workingSet) {
		synchronized (updaters) {
			final IWorkingSetUpdater updater = (IWorkingSetUpdater) updaters
					.get(workingSet.getId());
			if (updater != null) {
				SafeRunner.run(new WorkingSetRunnable() {

					@Override
					public void run() throws Exception {
						updater.remove(workingSet);
					}});
			}
		}
    }
    
    
    @Override
	public IWorkingSetSelectionDialog createWorkingSetSelectionDialog(Shell parent, boolean multi, String[] workingsSetIds) {
        return new WorkingSetSelectionDialog(parent, multi, workingsSetIds);
    }

	public void saveState(File stateFile) throws IOException {
		XMLMemento memento = XMLMemento
				.createWriteRoot(IWorkbenchConstants.TAG_WORKING_SET_MANAGER);
		saveWorkingSetState(memento);
		saveMruList(memento);
	
		FileOutputStream stream = new FileOutputStream(stateFile);
		OutputStreamWriter writer = new OutputStreamWriter(stream, "utf-8"); //$NON-NLS-1$
		memento.save(writer);
		writer.close();
	
	}
	
	@Override
	public void addExtension(IExtensionTracker tracker, IExtension extension) {
		
	}
	
	@Override
	public void removeExtension(IExtension extension, Object[] objects) {
		for (int i = 0; i < objects.length; i++) {
			Object object = objects[i];
			if (object instanceof IWorkingSetUpdater) {
				removeUpdater((IWorkingSetUpdater)object);
				
			}
			if (object instanceof IWorkingSetElementAdapter) {
				removeElementAdapter((IWorkingSetElementAdapter) object);
			}
		}
	}

	private void removeElementAdapter(
			final IWorkingSetElementAdapter elementAdapter) {
		SafeRunner.run(new WorkingSetRunnable() {

			@Override
			public void run() throws Exception {
				elementAdapter.dispose();

			}
		});
		synchronized (elementAdapters) {
			elementAdapters.values().remove(elementAdapter);
		}
	}

	private void removeUpdater(final IWorkingSetUpdater updater) {
		SafeRunner.run(new WorkingSetRunnable() {

			@Override
			public void run() throws Exception {
				updater.dispose();

			}
		});
		synchronized (updaters) {
			updaters.values().remove(updater);
		}
		firePropertyChange(IWorkingSetManager.CHANGE_WORKING_SET_UPDATER_UNINSTALLED, updater, null);
	}
	
	@Override
	public void addToWorkingSets(final IAdaptable element, IWorkingSet[] workingSets) {
		for (int i = 0; i < workingSets.length; i++) {
			final IWorkingSet workingSet = workingSets[i];
			SafeRunner.run(new WorkingSetRunnable() {

				@Override
				public void run() throws Exception {
					IAdaptable[] adaptedNewElements = workingSet
							.adaptElements(new IAdaptable[] { element });
					if (adaptedNewElements.length == 1) {
						IAdaptable[] elements = workingSet.getElements();
						IAdaptable[] newElements = new IAdaptable[elements.length + 1];
						System.arraycopy(elements, 0, newElements, 0,
								elements.length);
						newElements[newElements.length - 1] = adaptedNewElements[0];
						workingSet.setElements(newElements);
					}
				}});
		}
	}

	@Override
	public void setRecentWorkingSetsLength(int length) {
		if (length < 1 || length > 99)
			throw new IllegalArgumentException("Invalid recent working sets length: " + length); //$NON-NLS-1$
		IPreferenceStore store = PrefUtil.getAPIPreferenceStore();
		store.setValue(IWorkbenchPreferenceConstants.RECENTLY_USED_WORKINGSETS_SIZE, length);
		sizeRecentWorkingSets();
	}

	private void sizeRecentWorkingSets() {
		int maxLength = getRecentWorkingSetsLength();
		while (recentWorkingSets.size() > maxLength) {
			int lastPosition = recentWorkingSets.size() - 1;
			recentWorkingSets.remove(lastPosition);
		}
	}

	@Override
	public int getRecentWorkingSetsLength() {
		IPreferenceStore store = PrefUtil.getAPIPreferenceStore();
		return store.getInt(IWorkbenchPreferenceConstants.RECENTLY_USED_WORKINGSETS_SIZE);
	}
}
