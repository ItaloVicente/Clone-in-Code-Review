package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.internal.provisional.application.IActionBarConfigurer2;
import org.eclipse.ui.internal.registry.IActionSet;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;

public class ActionPresentation {
    private WorkbenchWindow window;

    private HashMap mapDescToRec = new HashMap(3);

    private HashMap invisibleBars = new HashMap(3);

    private class SetRec {
        public SetRec(IActionSet set,
                SubActionBars bars) {
            this.set = set;
            this.bars = bars;
        }

        public IActionSet set;

        public SubActionBars bars;
    }

    public ActionPresentation(WorkbenchWindow window) {
        super();
        this.window = window;
    }

    public void clearActionSets() {
        final List oldList = new ArrayList();
        oldList.addAll(mapDescToRec.keySet());
        oldList.addAll(invisibleBars.keySet());

        Iterator iter = oldList.iterator();
        while (iter.hasNext()) {
            IActionSetDescriptor desc = (IActionSetDescriptor) iter.next();
            removeActionSet(desc);
        }
    }

    public void removeActionSet(IActionSetDescriptor desc) {
        SetRec rec = (SetRec) mapDescToRec.remove(desc);
        if (rec == null) {
            rec = (SetRec) invisibleBars.remove(desc);
        }
        if (rec != null) {
            IActionSet set = rec.set;
            SubActionBars bars = rec.bars;
            if (bars != null) {
                bars.dispose();
            }
            if (set != null) {
                set.dispose();
            }
        }
    }

    public void setActionSets(IActionSetDescriptor[] newArray) {
        HashSet newList = new HashSet();
        
        for (int i = 0; i < newArray.length; i++) {
            IActionSetDescriptor descriptor = newArray[i];
            
            newList.add(descriptor);
        }
        List oldList = new ArrayList(mapDescToRec.keySet());

        Iterator iter = oldList.iterator();
        while (iter.hasNext()) {
            IActionSetDescriptor desc = (IActionSetDescriptor) iter.next();
            if (!newList.contains(desc)) {
                SetRec rec = (SetRec) mapDescToRec.get(desc);
                if (rec != null) {
                    mapDescToRec.remove(desc);
                    IActionSet set = rec.set;
                    SubActionBars bars = rec.bars;
                    if (bars != null) {
                        SetRec invisibleRec = new SetRec(set, bars);
                        invisibleBars.put(desc, invisibleRec);
                        bars.deactivate();
                    }
                }
            }
        }

        ArrayList sets = new ArrayList();
        
        for (int i = 0; i < newArray.length; i++) {
            IActionSetDescriptor desc = newArray[i];

            if (!mapDescToRec.containsKey(desc)) {
                try {
                    SetRec rec;
                    if (invisibleBars.containsKey(desc)) {
                        rec = (SetRec) invisibleBars.get(desc);
                        if (rec.bars != null) {
                            rec.bars.activate();
                        }
                        invisibleBars.remove(desc);
                    } else {
                        IActionSet set = desc.createActionSet();
                        SubActionBars bars = new ActionSetActionBars(window
								.getActionBars(), window,
								(IActionBarConfigurer2) window.getWindowConfigurer()
										.getActionBarConfigurer(), desc.getId());
                        rec = new SetRec(set, bars);
                        set.init(window, bars);
                        sets.add(set);

                        Object[] existingRegistrations = window
                                .getExtensionTracker().getObjects(
                                        desc.getConfigurationElement()
                                                .getDeclaringExtension());
                        if (existingRegistrations.length == 0
                                || !containsRegistration(existingRegistrations,
                                        desc)) {
                            window.getExtensionTracker().registerObject(
                                    desc.getConfigurationElement().getDeclaringExtension(),
                                    desc, IExtensionTracker.REF_WEAK);
                        }
                    }
                    mapDescToRec.put(desc, rec);
                } catch (CoreException e) {
                    WorkbenchPlugin
                            .log("Unable to create ActionSet: " + desc.getId(), e);//$NON-NLS-1$
                }
            }
        }
        PluginActionSetBuilder.processActionSets(sets, window);

        iter = sets.iterator();
        while (iter.hasNext()) {
            PluginActionSet set = (PluginActionSet) iter.next();
            set.getBars().activate();
        }
    }

    private boolean containsRegistration(Object[] existingRegistrations, IActionSetDescriptor set) {
        for (int i = 0; i < existingRegistrations.length; i++) {
            if (existingRegistrations[i] == set) {
				return true;
			}
        }
        return false;
    }

    public IActionSet[] getActionSets() {
        Collection setRecCollection = mapDescToRec.values();
        IActionSet result[] = new IActionSet[setRecCollection.size()];
        int i = 0;
        for (Iterator iterator = setRecCollection.iterator(); iterator
                .hasNext(); i++) {
			result[i] = ((SetRec) iterator.next()).set;
		}
        return result;
    }
}
