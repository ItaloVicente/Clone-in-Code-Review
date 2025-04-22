package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.registry.ActionSetDescriptor;
import org.eclipse.ui.internal.registry.IActionSet;
import org.eclipse.ui.services.IDisposable;

public class PluginActionSet implements IActionSet {
    private ActionSetDescriptor desc;

    private ArrayList pluginActions = new ArrayList(4);

    private ActionSetActionBars bars;

	private IDisposable disposableBuilder;

    public PluginActionSet(ActionSetDescriptor desc) {
        super();
        this.desc = desc;
    }

    public void addPluginAction(WWinPluginAction action) {
        pluginActions.add(action);
    }

    public IAction[] getPluginActions() {
        IAction result[] = new IAction[pluginActions.size()];
        pluginActions.toArray(result);
        return result;
    }

    @Override
	public void dispose() {
        Iterator iter = pluginActions.iterator();
        while (iter.hasNext()) {
            WWinPluginAction action = (WWinPluginAction) iter.next();
            action.dispose();
        }
        pluginActions.clear();
        bars = null;
		if (disposableBuilder != null) {
			disposableBuilder.dispose();
			disposableBuilder = null;
		}
    }

        return bars;
    }

    public IConfigurationElement getConfigElement() {
        return desc.getConfigurationElement();
    }

    public ActionSetDescriptor getDesc() {
        return desc;
    }

    @Override
	public void init(IWorkbenchWindow window, IActionBars bars) {
        this.bars = (ActionSetActionBars) bars;
    }

	public void setBuilder(IDisposable builder) {
		if (disposableBuilder != null) {
			disposableBuilder.dispose();
		}
		disposableBuilder = builder;
	}

	@Override
	public String toString() {
		return "PluginActionSet [desc=" + desc + ", " //$NON-NLS-1$ //$NON-NLS-2$
				+ (pluginActions != null ? "actions=" + pluginActions : "") + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

}
