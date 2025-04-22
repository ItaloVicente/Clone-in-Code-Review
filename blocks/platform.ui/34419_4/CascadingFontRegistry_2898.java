package org.eclipse.ui.internal.themes;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class CascadingColorRegistry extends ColorRegistry {

    private ColorRegistry parent;

    private IPropertyChangeListener listener = new IPropertyChangeListener() {
        @Override
		public void propertyChange(PropertyChangeEvent event) {
			if (!hasOverrideFor(event.getProperty()))
				fireMappingChanged(event.getProperty(), event.getOldValue(),
						event.getNewValue());
        }
    };

    public CascadingColorRegistry(ColorRegistry parent) {
    	super(Display.getCurrent(), false);
        this.parent = parent;
        parent.addListener(listener);
    }

    @Override
	public Color get(String symbolicName) {
        if (super.hasValueFor(symbolicName)) {
			return super.get(symbolicName);
		}
        
        return parent.get(symbolicName);
    }

    @Override
	public Set getKeySet() {
        Set keyUnion = new HashSet(super.getKeySet());
        keyUnion.addAll(parent.getKeySet());
        return keyUnion;
    }

    @Override
	public RGB getRGB(String symbolicName) {
        if (super.hasValueFor(symbolicName)) {
			return super.getRGB(symbolicName);
		}
        
        return parent.getRGB(symbolicName);
    }

    @Override
	public boolean hasValueFor(String colorKey) {
        return super.hasValueFor(colorKey) || parent.hasValueFor(colorKey);
    }

    public boolean hasOverrideFor(String colorKey) {
        return super.hasValueFor(colorKey);
    }

    public void dispose() {
        parent.removeListener(listener);
        PlatformUI.getWorkbench().getDisplay().asyncExec(displayRunnable);
    }
}
