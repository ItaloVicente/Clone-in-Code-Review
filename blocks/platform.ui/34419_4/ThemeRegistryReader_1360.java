package org.eclipse.ui.internal.themes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.ui.themes.IThemeManager;

public class ThemeRegistry implements IThemeRegistry {

    private List themes;

    private List colors;

    private List fonts;

    private List categories;

    private Map dataMap;

    private Map categoryBindingMap;

    public ThemeRegistry() {
        themes = new ArrayList();
        colors = new ArrayList();
        fonts = new ArrayList();
        categories = new ArrayList();
        dataMap = new HashMap();
        categoryBindingMap = new HashMap();
    }

    void add(IThemeDescriptor desc) {
        if (findTheme(desc.getId()) != null) {
			return;
		}
        themes.add(desc);
    }

    void add(ColorDefinition desc) {
    	addOrReplaceDescriptor(colors, desc);
    }

    @Override
	public ThemeElementCategory findCategory(String id) {
        return (ThemeElementCategory) findDescriptor(getCategories(), id);
    }

    @Override
	public ColorDefinition findColor(String id) {
        return (ColorDefinition) findDescriptor(getColors(), id);
    }

    @Override
	public IThemeDescriptor findTheme(String id) {
        return (IThemeDescriptor) findDescriptor(getThemes(), id);
    }

    private IThemeElementDefinition findDescriptor(
            IThemeElementDefinition[] descriptors, String id) {
        int idx = Arrays.binarySearch(descriptors, id, ID_COMPARATOR);
        if (idx < 0) {
			return null;
		}
        return descriptors[idx];
    }

    private IThemeElementDefinition addOrReplaceDescriptor(
            List descriptors, IThemeElementDefinition newElement) {
    	String id = newElement.getId();
    	for (int i = 0; i < descriptors.size(); i++) {
    		IThemeElementDefinition existingElement = (IThemeElementDefinition) descriptors.get(i);
    		if(existingElement.getId().equals(id)) {
    			descriptors.remove(i);
    			descriptors.add(newElement);
    			return existingElement;
    		}
		}
    	descriptors.add(newElement);
        return null;
    }
    
    @Override
	public IThemeDescriptor[] getThemes() {
        int nSize = themes.size();
        IThemeDescriptor[] retArray = new IThemeDescriptor[nSize];
        themes.toArray(retArray);
        Arrays.sort(retArray, ID_COMPARATOR);
        return retArray;
    }

    @Override
	public ColorDefinition[] getColors() {
        int nSize = colors.size();
        ColorDefinition[] retArray = new ColorDefinition[nSize];
        colors.toArray(retArray);
        Arrays.sort(retArray, ID_COMPARATOR);
        return retArray;
    }

    @Override
	public ColorDefinition[] getColorsFor(String themeId) {
        ColorDefinition[] defs = getColors();
        if (themeId.equals(IThemeManager.DEFAULT_THEME)) {
			return defs;
		}

        IThemeDescriptor desc = findTheme(themeId);
        ColorDefinition[] overrides = desc.getColors();
        return (ColorDefinition[]) overlay(defs, overrides);
    }

    @Override
	public FontDefinition[] getFontsFor(String themeId) {
        FontDefinition[] defs = getFonts();
        if (themeId.equals(IThemeManager.DEFAULT_THEME)) {
			return defs;
		}

        IThemeDescriptor desc = findTheme(themeId);
        FontDefinition[] overrides = desc.getFonts();
        return (FontDefinition[]) overlay(defs, overrides);
    }

    private IThemeElementDefinition[] overlay(IThemeElementDefinition[] defs,
            IThemeElementDefinition[] overrides) {
        for (int i = 0; i < overrides.length; i++) {
            int idx = Arrays.binarySearch(defs, overrides[i],
                    IThemeRegistry.ID_COMPARATOR);
            if (idx >= 0) {
                defs[idx] = overlay(defs[idx], overrides[i]);
            }
        }
        return defs;
    }

    private IThemeElementDefinition overlay(IThemeElementDefinition original,
            IThemeElementDefinition overlay) {
        if (original instanceof ColorDefinition) {
            ColorDefinition originalColor = (ColorDefinition) original;
            ColorDefinition overlayColor = (ColorDefinition) overlay;
            return new ColorDefinition(originalColor, overlayColor.getValue());
        } else if (original instanceof FontDefinition) {
            FontDefinition originalFont = (FontDefinition) original;
            FontDefinition overlayFont = (FontDefinition) overlay;
            return new FontDefinition(originalFont, overlayFont.getValue());
        }
        return null;
    }

    void add(FontDefinition definition) {
        if (findFont(definition.getId()) != null) {
			return;
		}
        fonts.add(definition);
    }

    @Override
	public FontDefinition[] getFonts() {
        int nSize = fonts.size();
        FontDefinition[] retArray = new FontDefinition[nSize];
        fonts.toArray(retArray);
        Arrays.sort(retArray, ID_COMPARATOR);
        return retArray;
    }

    @Override
	public FontDefinition findFont(String id) {
        return (FontDefinition) findDescriptor(getFonts(), id);
    }

    void add(ThemeElementCategory definition) {
        if (findCategory(definition.getId()) != null) {
			return;
		}
        categories.add(definition);
    }

    @Override
	public ThemeElementCategory[] getCategories() {
        int nSize = categories.size();
        ThemeElementCategory[] retArray = new ThemeElementCategory[nSize];
        categories.toArray(retArray);
        Arrays.sort(retArray, ID_COMPARATOR);
        return retArray;
    }

    void setData(String name, String value) {
        if (dataMap.containsKey(name)) {
			return;
		}
        
        dataMap.put(name, value);
    }

    @Override
	public Map getData() {
        return Collections.unmodifiableMap(dataMap);
    }

    public void addData(Map otherData) {
        for (Iterator i = otherData.keySet().iterator(); i.hasNext();) {
            Object key = i.next();
            if (dataMap.containsKey(key)) {
				continue;
			}
            dataMap.put(key, otherData.get(key));
        }
    }

    @Override
	public Set getPresentationsBindingsFor(ThemeElementCategory category) {
        return (Set) categoryBindingMap.get(category.getId());
    }
}
