/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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

/**
 * Manage the configurable actions for one window.
 */
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

    /**
     * ActionPresentation constructor comment.
     */
    public ActionPresentation(WorkbenchWindow window) {
        super();
        this.window = window;
    }

    /**
     * Remove all action sets.
     */
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

    /**
     * Destroy an action set.
     */
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

    /**
     * Sets the list of visible action set.
     */
    public void setActionSets(IActionSetDescriptor[] newArray) {
        HashSet newList = new HashSet();

        for (IActionSetDescriptor descriptor : newArray) {
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

        for (IActionSetDescriptor element : newArray) {
            IActionSetDescriptor desc = element;

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

    /**
     * Return whether the array contains the given action set.
     *
     * @param existingRegistrations the array to check
     * @param set the set to look for
     * @return whether the set is in the array
     * @since 3.1
     */
    private boolean containsRegistration(Object[] existingRegistrations, IActionSetDescriptor set) {
        for (Object existingRegistration : existingRegistrations) {
            if (existingRegistration == set) {
				return true;
			}
        }
        return false;
    }

    /**
     */
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
