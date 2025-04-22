package org.eclipse.ui.internal.themes;

import org.eclipse.e4.ui.internal.css.swt.definition.IFontDefinitionOverridable;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.PlatformUI;

public class FontDefinition extends ThemeElementDefinition implements
		IHierarchalThemeElementDefinition, ICategorizedThemeElementDefinition, IEditable,
		IFontDefinitionOverridable {

	private String defaultsTo;

    private String value;

	private String defaultValue;

    private boolean isEditable;

    private FontData[] parsedValue;

    public FontDefinition(String fontName, String uniqueId, String defaultsId,
            String value, String categoryId, boolean isEditable,
            String fontDescription) {
		super(uniqueId, fontName, fontDescription, categoryId);
        this.defaultsTo = defaultsId;
        this.value = value;
        this.isEditable = isEditable;
    }

    public FontDefinition(FontDefinition originalFont, FontData[] datas) {
		super(originalFont.getId(), originalFont.getName(), originalFont.getDescription(),
				originalFont.getCategoryId());
        this.isEditable = originalFont.isEditable();
        this.parsedValue = datas;
    }

    @Override
	public String getDefaultsTo() {
        return defaultsTo;
    }

    @Override
	public FontData[] getValue() {
        if (value == null) {
			return null;
		}
        if (parsedValue == null) {
            parsedValue = JFaceResources.getFontRegistry().filterData(
                    StringConverter.asFontDataArray(value),
                    PlatformUI.getWorkbench().getDisplay());
        }

        return parsedValue;
    }

	@Override
	public void resetToDefaultValue() {
		value = defaultValue;
		parsedValue = null;
		super.resetToDefaultValue();
	}

    @Override
	public boolean isEditable() {
        return isEditable;
    }
    
    @Override
	public boolean equals(Object obj) {
        if (obj instanceof FontDefinition) {
            return getId().equals(((FontDefinition)obj).getId());
        }
        return false;
    }
    
    @Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public void setValue(FontData[] data) {
		if (data != null && data.length > 0) {
			if (defaultValue == null) {
				defaultValue = value;
			}
			value = data[0].getName();
			parsedValue = data;
			appendState(State.OVERRIDDEN);
		}
	}
}
