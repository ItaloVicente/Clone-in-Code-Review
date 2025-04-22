
package org.eclipse.ui.internal.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

public class WorkbenchPreferenceExpressionNode extends PreferenceNode 
	implements IPluginContribution {
	
	
	public WorkbenchPreferenceExpressionNode(String id) {
		super(id);
	}
	
    @Override
	public IPreferenceNode findSubNode(String id) {
        return getNodeExpression(super.findSubNode(id));
    }

    @Override
	public IPreferenceNode[] getSubNodes() {
    	IPreferenceNode[] prefNodes = super.getSubNodes();
        int size = prefNodes.length;
        List list = new ArrayList(size);
        for (int i = 0; i < size; i++) {
        	IPreferenceNode prefNode = getNodeExpression(prefNodes[i]);
            if (prefNode != null) {
                list.add(prefNode);
            }
        }
        return (IPreferenceNode[])list.toArray(new IPreferenceNode[list.size()]);
    }

    public static IPreferenceNode getNodeExpression(
    		IPreferenceNode prefNode) {
    	if (prefNode == null)
    		return null;
        if (prefNode instanceof WorkbenchPreferenceExtensionNode) {
        	WorkbenchPreferenceExpressionNode node = 
        		(WorkbenchPreferenceExtensionNode)prefNode;
            if (WorkbenchActivityHelper.restrictUseOf(node)) {
                return null;
            }
        }
        return prefNode;
    }

	@Override
	public String getLocalId() {
		return getId();
	}

	@Override
	public String getPluginId() {
		return ""; //$NON-NLS-1$
	}
}
