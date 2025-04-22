package org.eclipse.ui.internal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.jface.resource.DeviceResourceDescriptor;
import org.eclipse.jface.resource.DeviceResourceException;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.internal.WorkbenchPlugin;

public final class Descriptors {
    private static final String DISPOSE_LIST = "Descriptors.disposeList"; //$NON-NLS-1$
    
    private Descriptors() {
    }
    
    private static final class ResourceMethod {
        ResourceMethod(Method m, String id) {
            method = m;
            this.id = id;
        }
        
        Method method;
        DeviceResourceDescriptor oldDescriptor;
        String id;
                
        public void invoke(Widget toCall, DeviceResourceDescriptor newDescriptor) {
            if (newDescriptor == oldDescriptor) {
                return;
            }
            
            ResourceManager mgr = JFaceResources.getResources(toCall.getDisplay());
            
            Object newResource;
            try {
                newResource = newDescriptor == null? null : mgr.create(newDescriptor);
            } catch (DeviceResourceException e1) {
                WorkbenchPlugin.log(e1);
                return;
            }
            
            try {
                method.invoke(toCall, new Object[] {newResource});
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (IllegalAccessException e) {
                WorkbenchPlugin.log(e);
                return;
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof RuntimeException) {
                    throw (RuntimeException)e.getTargetException();
                }
                WorkbenchPlugin.log(e);
                return;
            }
            
            if (oldDescriptor != null) {
                mgr.destroy(oldDescriptor);
            }
            
            
            oldDescriptor = newDescriptor;            
        }

        public void dispose() {
            if (oldDescriptor != null) {
                ResourceManager mgr = JFaceResources.getResources();
                mgr.destroy(oldDescriptor);
                oldDescriptor = null;
            }                

        }
    }
    
    private static DisposeListener disposeListener = new DisposeListener() {
        @Override
		public void widgetDisposed(DisposeEvent e) {
            doDispose(e.widget);
        }
    };
    
   
    public static void setImage(Item item, ImageDescriptor descriptor) {
        callMethod(item, "setImage", descriptor, Image.class); //$NON-NLS-1$
    }
    
   
    public static void setHotImage(ToolItem item, ImageDescriptor descriptor) {
        callMethod(item, "setHotImage", descriptor, Image.class); //$NON-NLS-1$
    }

    public static void setDisabledImage(ToolItem item, ImageDescriptor descriptor) {
        callMethod(item, "setDisabledImage", descriptor, Image.class); //$NON-NLS-1$
    }

    
    public static void setFont(TableItem item, FontDescriptor descriptor) {
        callMethod(item, "setFont", descriptor, Font.class); //$NON-NLS-1$
    }
    
    public static void setBackground(TableItem item, ColorDescriptor descriptor) {
        callMethod(item, "setBackground", descriptor, Color.class); //$NON-NLS-1$
    }

    public static void setForeground(TableItem item, ColorDescriptor descriptor) {
        callMethod(item, "setForeground", descriptor, Color.class); //$NON-NLS-1$
    }
    
    
    public static void setBackground(Control control, ColorDescriptor descriptor) {
        callMethod(control, "setBackground", descriptor, Color.class); //$NON-NLS-1$
    }
    
    public static void setForeground(Control control, ColorDescriptor descriptor) {
        callMethod(control, "setForeground", descriptor, Color.class); //$NON-NLS-1$
    }
    
    
    public static void setImage(Button button, ImageDescriptor descriptor) {
        callMethod(button, "setImage", descriptor, Image.class); //$NON-NLS-1$
    }

    public static void setImage(Label label, ImageDescriptor descriptor) {
        callMethod(label, "setImage", descriptor, Image.class); //$NON-NLS-1$
    }
    
    private static ResourceMethod getResourceMethod(Widget toCall, String methodName, Class resourceType) throws NoSuchMethodException {
        Object oldData = toCall.getData(DISPOSE_LIST);
        
        if (oldData instanceof List) {
            for (Iterator iter = ((List)oldData).iterator(); iter.hasNext();) {
                ResourceMethod method = (ResourceMethod) iter.next();
                
                if (method.id == methodName) {
                    return method;
                }
            }
        } if (oldData instanceof ResourceMethod) {
            if (((ResourceMethod)oldData).id == methodName) {
                return ((ResourceMethod)oldData); 
            }
            
            List newList = new ArrayList();
            newList.add(oldData);
            oldData = newList;
            toCall.setData(DISPOSE_LIST, oldData);
        }
        
        
        Class clazz = toCall.getClass();
        
        Method method;
        try {
            method = clazz.getMethod(methodName, new Class[] {resourceType});
        } catch (SecurityException e) {
            throw e;
        }
        
        ResourceMethod result = new ResourceMethod(method, methodName);

        if (oldData == null) {
            toCall.setData(DISPOSE_LIST, result);
            toCall.addDisposeListener(disposeListener);
        } else {
            ((List)oldData).add(result);
        }
        
        return result;
    }
    
    private static void callMethod(Widget toCall, String methodName, DeviceResourceDescriptor descriptor, Class resourceType) {
        ResourceMethod method;
        try {
            method = getResourceMethod(toCall, methodName, resourceType);
        } catch (NoSuchMethodException e) {
            WorkbenchPlugin.log(e);
            return;
        }
       
        method.invoke(toCall, descriptor);        
    }
    
    private static void doDispose(Widget widget) {
        Object oldData = widget.getData(DISPOSE_LIST);
        
        if (oldData instanceof ArrayList) {
            ArrayList list = ((ArrayList)oldData);
            ResourceMethod[] data = (ResourceMethod[]) list.toArray(new ResourceMethod[list.size()]);
            
            for (int i = 0; i < data.length; i++) {
                ResourceMethod method = data[i];

                method.dispose();                
            }
        } 
        
        if (oldData instanceof ResourceMethod) {
            ((ResourceMethod)oldData).dispose();
        }
    }
    
}
