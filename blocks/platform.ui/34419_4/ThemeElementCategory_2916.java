package org.eclipse.ui.internal.themes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;

public class ThemeDescriptor implements IThemeDescriptor {

    public static final String ATT_ID = "id";//$NON-NLS-1$

    private static final String ATT_NAME = "name";//$NON-NLS-1$	

    private Collection colors = new HashSet();

    private String description;

    private Collection fonts = new HashSet();

    private String id;

    private String name;

    private Map dataMap = new HashMap();

    public ThemeDescriptor(String id) {
        this.id = id;
    }

    void add(ColorDefinition definition) {
    	if (colors.contains(definition)) {
    		colors.remove(definition);
		}
        colors.add(definition);
    }

    void add(FontDefinition definition) {
        if (fonts.contains(definition)) {
			return;
		}
        fonts.add(definition);
    }

    void setData(String key, Object data) {
        if (dataMap.containsKey(key)) {
			return;
		}
            
        dataMap.put(key, data);
    }

    @Override
	public ColorDefinition[] getColors() {
        ColorDefinition[] defs = (ColorDefinition[]) colors
                .toArray(new ColorDefinition[colors.size()]);
        Arrays.sort(defs, IThemeRegistry.ID_COMPARATOR);
        return defs;
    }

    @Override
	public String getDescription() {
        return description;
    }

    @Override
	public FontDefinition[] getFonts() {
        FontDefinition[] defs = (FontDefinition[]) fonts
                .toArray(new FontDefinition[fonts.size()]);
        Arrays.sort(defs, IThemeRegistry.ID_COMPARATOR);
        return defs;
    }

    @Override
	public String getId() {
        return id;
    }

    @Override
	public String getName() {
    	if (name == null)
    		return getId();
        return name;
    }

    void extractName(IConfigurationElement configElement) {
        if (name == null) {
			name = configElement.getAttribute(ATT_NAME);
		}
    }

    void setDescription(String description) {
        if (this.description == null) {
			this.description = description;
		}
    }

    @Override
	public Map getData() {
        return Collections.unmodifiableMap(dataMap);
    }
}
