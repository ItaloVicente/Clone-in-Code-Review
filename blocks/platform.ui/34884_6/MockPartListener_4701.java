package org.eclipse.ui.tests.api;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.tests.harness.util.CallHistory;
import org.osgi.framework.Bundle;

public class MockPart extends EventManager implements IExecutableExtension {

    public MockPart() {
        callTrace = new CallHistory(this);
        selectionProvider = new MockSelectionProvider();
    }

    protected CallHistory callTrace;

    protected MockSelectionProvider selectionProvider;

    private IConfigurationElement config;

    private Object data;

    private Image titleImage;

    private DisposeListener disposeListener = new DisposeListener() {
    	@Override
		public void widgetDisposed(DisposeEvent e) {
    		MockPart.this.widgetDisposed();
    	}
    };
    
    public CallHistory getCallHistory() {
        return callTrace;
    }

    public ISelectionProvider getSelectionProvider() {
        return selectionProvider;
    }

    @Override
	public void setInitializationData(IConfigurationElement config,
 String propertyName, Object data) {
    	
    	callTrace.add("setInitializationData");
    	
        this.config = config;
        this.data = data;

        String strIcon = config.getAttribute("icon");//$NON-NLS-1$
        if (strIcon != null) {
            try {
            	Bundle plugin = Platform.getBundle(config.getNamespace());
                URL installURL = plugin.getEntry("/"); //$NON-NLS-1$
                URL fullPathString = new URL(installURL, strIcon);
                ImageDescriptor imageDesc = ImageDescriptor
                        .createFromURL(fullPathString);
                titleImage = imageDesc.createImage();
            } catch (MalformedURLException e) {
            }
        }
    }

    protected IConfigurationElement getConfig() {
        return config;
    }

    protected Object getData() {
        return data;
    }

    public void widgetDisposed() {
    	callTrace.add("widgetDisposed");
    }
    
    public void addPropertyListener(IPropertyListener listener) {
        addListenerObject(listener);
    }

    public void createPartControl(Composite parent) {
        callTrace.add("createPartControl");
        
        parent.addDisposeListener(disposeListener);
    }

    public void dispose() {
        callTrace.add("dispose");
    }

    public Image getTitleImage() {
        return titleImage;
    }

    public void removePropertyListener(IPropertyListener listener) {
        removeListenerObject(listener);
    }

    public void setFocus() {
        callTrace.add("setFocus");
    }

    public Object getAdapter(Class arg0) {
        return null;
    }

    public void fireSelection() {
        selectionProvider.fireSelection();
    }

    protected void firePropertyChange(int propertyId) {
        Object[] listeners = getListeners();
        for (int i = 0; i < listeners.length; i++) {
            IPropertyListener l = (IPropertyListener) listeners[i];
            l.propertyChanged(this, propertyId);
        }
    }

    private boolean siteState = false;

    protected void setSiteInitialized(boolean initialized) {
        siteState = initialized;
    }

    public boolean isSiteInitialized() {
        return siteState;
    }
}
