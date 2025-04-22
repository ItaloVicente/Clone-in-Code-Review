
package org.eclipse.ui.internal.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.KeywordRegistry;
import org.eclipse.ui.model.IComparableContribution;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public abstract class WorkbenchPreferenceExtensionNode extends WorkbenchPreferenceExpressionNode 
    implements IComparableContribution {
	
	private Collection keywordReferences;
	
	private IConfigurationElement configurationElement;

	private ImageDescriptor imageDescriptor;

	private Image image;

	private Collection keywordLabelCache;
	
	private int priority;

	private String pluginId;

	public WorkbenchPreferenceExtensionNode(String id, IConfigurationElement configurationElement) {
		super(id);
		this.configurationElement = configurationElement;
		this.pluginId = configurationElement.getNamespaceIdentifier();
	}

	public Collection getKeywordReferences() {
		if (keywordReferences == null) {
			IConfigurationElement[] references = getConfigurationElement()
					.getChildren(IWorkbenchRegistryConstants.TAG_KEYWORD_REFERENCE);
			HashSet list = new HashSet(references.length);
			for (int i = 0; i < references.length; i++) {
				IConfigurationElement page = references[i];
				String id = page.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
				if (id != null) {
					list.add(id);
				}
			}

			if (!list.isEmpty()) {
				keywordReferences = list;
			} else {
				keywordReferences = Collections.EMPTY_SET;
			}
			
		}
		return keywordReferences;
	}

	public Collection getKeywordLabels() {
		if (keywordLabelCache != null) {
			return keywordLabelCache;
		}
		
		Collection refs = getKeywordReferences();
		
		if(refs == Collections.EMPTY_SET) {
			keywordLabelCache = Collections.EMPTY_SET; 
			return keywordLabelCache;
		}
		
		keywordLabelCache = new ArrayList(refs.size());
		Iterator referenceIterator = refs.iterator();
		while(referenceIterator.hasNext()){
			Object label = KeywordRegistry.getInstance().getKeywordLabel(
					(String) referenceIterator.next());
			if(label != null) {
				keywordLabelCache.add(label);
			}
		}
		
		return keywordLabelCache;
	}
	
	public void clearKeywords() {
		keywordLabelCache = null;
	}

	@Override
	public void disposeResources() {
        if (image != null) {
            image.dispose();
            image = null;
        }
        super.disposeResources();
	}

	@Override
	public Image getLabelImage() {		
        if (image == null) {
        	ImageDescriptor desc = getImageDescriptor();
        	if (desc != null) {
				image = imageDescriptor.createImage();
			}
        }
        return image;
    }


	@Override
	public String getLabelText() {
		return getConfigurationElement().getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
	}

    @Override
	public ImageDescriptor getImageDescriptor() {
    	if (imageDescriptor != null) {
			return imageDescriptor;
		}
    	
    	String imageName = getConfigurationElement().getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
		if (imageName != null) {
			String contributingPluginId = pluginId;
			imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(contributingPluginId, imageName);
		}
		return imageDescriptor;
    }
    
	public IConfigurationElement getConfigurationElement() {
		return configurationElement;
	}
	
	@Override
	public String getLocalId() {
		return getId();
	}

	@Override
	public String getPluginId() {
		return pluginId;
	}

    @Override
	public Object getAdapter(Class adapter)
    {
        if (adapter == IConfigurationElement.class)
            return getConfigurationElement();
        return null;
    }

    @Override
	public String getLabel()
    {
        return getLabelText();
    }

    @Override
	public int getPriority()
    {
        return priority;
    }

    public void setPriority(int pri)
    {
        priority = pri;
    }


}
