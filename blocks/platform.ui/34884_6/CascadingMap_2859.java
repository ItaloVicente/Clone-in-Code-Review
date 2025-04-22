package org.eclipse.ui.internal.themes;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class CascadingFontRegistry extends FontRegistry {

    private FontRegistry parent;

    private IPropertyChangeListener listener = new IPropertyChangeListener() {
        @Override
		public void propertyChange(PropertyChangeEvent event) {
			if (!hasOverrideFor(event.getProperty()))
            fireMappingChanged(event.getProperty(), event.getOldValue(), event
                    .getNewValue());
			
        }
    };

    public CascadingFontRegistry(FontRegistry parent) {
    	super(Display.getCurrent(), false);
        this.parent = parent;
        parent.addListener(listener);
    }

    @Override
	public Font get(String symbolicName) {
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
	public FontData[] getFontData(String symbolicName) {
        if (super.hasValueFor(symbolicName)) {
			return super.getFontData(symbolicName);
		}
        return parent.getFontData(symbolicName);
    }

    @Override
	public boolean hasValueFor(String colorKey) {
        return super.hasValueFor(colorKey) || parent.hasValueFor(colorKey);
    }

    public boolean hasOverrideFor(String fontKey) {
        return super.hasValueFor(fontKey);
    }

    public void dispose() {
        parent.removeListener(listener);
        PlatformUI.getWorkbench().getDisplay().asyncExec(displayRunnable);
    }
}
