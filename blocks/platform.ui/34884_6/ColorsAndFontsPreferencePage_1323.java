package org.eclipse.ui.internal.themes;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.e4.ui.internal.css.swt.definition.IColorDefinitionOverridable;
import org.eclipse.jface.resource.DataFormatException;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.themes.ColorUtil;

public class ColorDefinition extends ThemeElementDefinition implements IPluginContribution,
		IHierarchalThemeElementDefinition, ICategorizedThemeElementDefinition, IEditable,
		IColorDefinitionOverridable {
    private static final RGB DEFAULT_COLOR_VALUE = new RGB(0,0,0);

	private String defaultsTo;

    private String pluginId;

    private String rawValue;

    boolean isEditable;

    private RGB parsedValue;

    public ColorDefinition(String label, String id, String defaultsTo,
            String value, String categoryId, boolean isEditable,
            String description, String pluginId) {
		super(id, label, description, categoryId);
        this.defaultsTo = defaultsTo;
        this.rawValue = value;
        this.isEditable = isEditable;
        this.pluginId = pluginId;
    }

    public ColorDefinition(ColorDefinition original, RGB value) {
		super(original.getId(), original.getName(), original.getDescription(), original
				.getCategoryId());
        this.isEditable = original.isEditable();
        this.pluginId = original.getPluginId();
        this.parsedValue = value;
    }

    @Override
	public String getDefaultsTo() {
        return defaultsTo;
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
	public RGB getValue() {
        if (parsedValue == null) {
			try {
				parsedValue = ColorUtil.getColorValue(rawValue);
			} catch (DataFormatException e) {
				parsedValue = DEFAULT_COLOR_VALUE;
				IStatus status = StatusUtil.newStatus(IStatus.WARNING,
						"Could not parse value for theme color " + getId(), e); //$NON-NLS-1$
				StatusManager.getManager().handle(status, StatusManager.LOG);
			}
		}
        return parsedValue;
    }

	@Override
	public void resetToDefaultValue() {
		parsedValue = null;
		super.resetToDefaultValue();
	}

    @Override
	public String toString() {
        return getId();
    }

    @Override
	public boolean isEditable() {
        return isEditable;
    }
    
    @Override
	public boolean equals(Object obj) {
        if (obj instanceof ColorDefinition) {
            return getId().equals(((ColorDefinition)obj).getId());
        }
        return false;
    }
    
    @Override
	public int hashCode() {
		return getId().hashCode();
    }

	@Override
	public void setValue(RGB data) {
		if (data != null) {
			parsedValue = data;
			appendState(State.OVERRIDDEN);
		}
	}
}
