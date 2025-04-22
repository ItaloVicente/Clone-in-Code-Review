package org.eclipse.ui.internal.themes;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.ui.themes.IThemeManager;

public final class ThemeElementHelper {
	public static void populateDefinition(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
			ITheme theme, FontRegistry registry, FontDefinition definition, IPreferenceStore store) {
		String key = createPreferenceKey(cssTheme, theme, definition.getId());
		String value = store.getString(key);
		if (!IPreferenceStore.STRING_DEFAULT_DEFAULT.equals(value)) {
			definition.appendState(ThemeElementDefinition.State.OVERRIDDEN);
			definition.appendState(ThemeElementDefinition.State.MODIFIED_BY_USER);
			registry.put(definition.getId(), PreferenceConverter.basicGetFontData(value));
		}
	}

    public static void populateRegistry(ITheme theme,
            FontDefinition[] definitions, IPreferenceStore store) {
        FontDefinition[] copyOfDefinitions = null;

        FontDefinition[] defaults = null;
        if (!theme.getId().equals(IThemeManager.DEFAULT_THEME)) {
            definitions = addDefaulted(definitions);
            if (store != null) {
				defaults = getDefaults(definitions);
			}
        }

        copyOfDefinitions = new FontDefinition[definitions.length];
        System.arraycopy(definitions, 0, copyOfDefinitions, 0,
                definitions.length);
        Arrays.sort(copyOfDefinitions, new IThemeRegistry.HierarchyComparator(
                definitions));

        for (int i = 0; i < copyOfDefinitions.length; i++) {
            FontDefinition definition = copyOfDefinitions[i];
            installFont(definition, theme, store, true);
        }

        if (defaults != null) {
            for (int i = 0; i < defaults.length; i++) {
                installFont(defaults[i], theme, store, false);
            }
        }
    }

    private static FontDefinition[] addDefaulted(FontDefinition[] definitions) {
        IThemeRegistry registry = WorkbenchPlugin.getDefault()
                .getThemeRegistry();
        FontDefinition[] allDefs = registry.getFonts();

        SortedSet set = addDefaulted(definitions, allDefs);
        return (FontDefinition[]) set.toArray(new FontDefinition[set.size()]);
    }

    private static void installFont(FontDefinition definition, ITheme theme,
            IPreferenceStore store, boolean setInRegistry) {
        FontRegistry registry = theme.getFontRegistry();
		Display display = PlatformUI.getWorkbench().getDisplay();

        String id = definition.getId();
        String key = createPreferenceKey(theme, id);
        FontData[] prefFont = store != null ? PreferenceConverter
                .getFontDataArray(store, key) : null;
        FontData[] defaultFont = null;
        if (definition.getValue() != null) {
			defaultFont = definition.getValue();
		} else if (definition.getDefaultsTo() != null) {
			String defaultsToKey = createPreferenceKey(theme, definition.getDefaultsTo());
			FontData[] defaultFontData = PreferenceConverter.getDefaultFontDataArray(store, defaultsToKey);
			defaultFont = registry.filterData(defaultFontData, display);
		} else {
			
			FontData[] fontData = JFaceResources.getFontRegistry().getFontData(
				display.getHighContrast()
					? JFaceResources.DEFAULT_FONT
					: id
			);
			defaultFont = registry.bestDataArray(fontData, display);
        }

        if (setInRegistry) {
			if (prefFont == null || prefFont == PreferenceConverter.FONTDATA_ARRAY_DEFAULT_DEFAULT) {
				if (definition.getValue() != null) {
					prefFont = definition.getValue();
				} else if (definition.getDefaultsTo() != null) {
					FontData[] fontData = registry.getFontData(definition.getDefaultsTo());
					prefFont = registry.filterData(fontData, display);
				} else {
					prefFont = defaultFont;
				}
            }

            if (prefFont != null) {
                registry.put(id, prefFont);
            }
        }

        if (defaultFont != null && store != null) {
            PreferenceConverter.setDefault(store, key, defaultFont);
        }
    }

	public static void populateDefinition(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
			ITheme theme, ColorRegistry registry, ColorDefinition definition, IPreferenceStore store) {
		String key = createPreferenceKey(cssTheme, theme, definition.getId());
		String value = store.getString(key);
		if (!IPreferenceStore.STRING_DEFAULT_DEFAULT.equals(value)) {
			definition.appendState(ThemeElementDefinition.State.OVERRIDDEN);
			definition.appendState(ThemeElementDefinition.State.MODIFIED_BY_USER);
			registry.put(definition.getId(), StringConverter.asRGB(value));
		}
	}

    public static void populateRegistry(ITheme theme,
            ColorDefinition[] definitions, IPreferenceStore store) {

        ColorDefinition[] copyOfDefinitions = null;

        ColorDefinition[] defaults = null;
        if (!theme.getId().equals(IThemeManager.DEFAULT_THEME)) {
            definitions = addDefaulted(definitions);
            if (store != null) {
				defaults = getDefaults(definitions);
			}
        }

        copyOfDefinitions = new ColorDefinition[definitions.length];
        System.arraycopy(definitions, 0, copyOfDefinitions, 0,
                definitions.length);
        Arrays.sort(copyOfDefinitions, new IThemeRegistry.HierarchyComparator(
                definitions));

        for (int i = 0; i < copyOfDefinitions.length; i++) {
            ColorDefinition definition = copyOfDefinitions[i];
            installColor(definition, theme, store, true);
        }

        if (defaults != null) {
            for (int i = 0; i < defaults.length; i++) {
                installColor(defaults[i], theme, store, false);
            }
        }
    }

    private static ColorDefinition[] getDefaults(ColorDefinition[] definitions) {
        IThemeRegistry registry = WorkbenchPlugin.getDefault()
                .getThemeRegistry();
        ColorDefinition[] allDefs = registry.getColors();

        SortedSet set = new TreeSet(IThemeRegistry.ID_COMPARATOR);
        set.addAll(Arrays.asList(allDefs));
        set.removeAll(Arrays.asList(definitions));
        return (ColorDefinition[]) set.toArray(new ColorDefinition[set.size()]);
    }

    private static FontDefinition[] getDefaults(FontDefinition[] definitions) {
        IThemeRegistry registry = WorkbenchPlugin.getDefault()
                .getThemeRegistry();
        FontDefinition[] allDefs = registry.getFonts();

        SortedSet set = new TreeSet(IThemeRegistry.ID_COMPARATOR);
        set.addAll(Arrays.asList(allDefs));
        set.removeAll(Arrays.asList(definitions));
        return (FontDefinition[]) set.toArray(new FontDefinition[set.size()]);
    }

    private static ColorDefinition[] addDefaulted(ColorDefinition[] definitions) {
        IThemeRegistry registry = WorkbenchPlugin.getDefault()
                .getThemeRegistry();
        ColorDefinition[] allDefs = registry.getColors();

        SortedSet set = addDefaulted(definitions, allDefs);
        return (ColorDefinition[]) set.toArray(new ColorDefinition[set.size()]);
    }

    private static SortedSet addDefaulted(
            IHierarchalThemeElementDefinition[] definitions,
            IHierarchalThemeElementDefinition[] allDefs) {
        SortedSet set = new TreeSet(IThemeRegistry.ID_COMPARATOR);
        set.addAll(Arrays.asList(definitions));
        
        IHierarchalThemeElementDefinition copy [] = new IHierarchalThemeElementDefinition[allDefs.length];
		System.arraycopy(allDefs, 0, copy, 0, allDefs.length);
		
        Arrays.sort(allDefs, new IThemeRegistry.HierarchyComparator(copy));
        for (int i = 0; i < allDefs.length; i++) {
            IHierarchalThemeElementDefinition def = allDefs[i];
            if (def.getDefaultsTo() != null) {
                if (set.contains(def.getDefaultsTo())) {
					set.add(def);
				}
            }
        }
        return set;
    }

    
    private static void installColor(ColorDefinition definition, ITheme theme,
            IPreferenceStore store, boolean setInRegistry) {

    	
    	ColorRegistry registry = theme.getColorRegistry();

        String id = definition.getId();
        String key = createPreferenceKey(theme, id);
        RGB prefColor = store != null 
        	? PreferenceConverter.getColor(store, key) 
        	: null;
		RGB defaultColor;
		if (definition.getValue() != null) {
			defaultColor = definition.getValue();
		} else if (definition.getDefaultsTo() != null) {
			String defaultsToKey = createPreferenceKey(theme, definition.getDefaultsTo());
			defaultColor = PreferenceConverter.getDefaultColor(store, defaultsToKey);
		} else {
			defaultColor = null;
		}
     
        if (defaultColor == null) {
			defaultColor = PreferenceConverter.COLOR_DEFAULT_DEFAULT;
		}
        	
		if (prefColor == null || prefColor == PreferenceConverter.COLOR_DEFAULT_DEFAULT) {
			if (definition.getValue() != null) {
				prefColor = definition.getValue();
			} else if (definition.getDefaultsTo() != null) {
				prefColor = registry.getRGB(definition.getDefaultsTo());
			} else {
				prefColor = defaultColor;
			}
        }

        if (setInRegistry) {
        	registry.put(id, prefColor);
        }

        if (store != null) {
            PreferenceConverter.setDefault(store, key, defaultColor);
        }
    }

    public static String createPreferenceKey(ITheme theme, String id) {
        String themeId = theme.getId();
        if (themeId.equals(IThemeManager.DEFAULT_THEME)) {
			return id;
		}

        return themeId + '.' + id;
    }

	public static String createPreferenceKey(org.eclipse.e4.ui.css.swt.theme.ITheme cssTheme,
			ITheme theme, String id) {
		String cssThemePrefix = cssTheme != null ? cssTheme.getId() + '.' : ""; //$NON-NLS-1$
		return cssThemePrefix + createPreferenceKey(theme, id);
	}

    public static String[] splitPropertyName(Theme theme, String property) {
    	IThemeDescriptor[] descriptors = WorkbenchPlugin.getDefault()
				.getThemeRegistry().getThemes();
		for (int i = 0; i < descriptors.length; i++) {
			IThemeDescriptor themeDescriptor = descriptors[i];
			String id = themeDescriptor.getId();
			if (property.startsWith(id + '.')) { // the property starts with
				return new String[] { property.substring(0, id.length()),
						property.substring(id.length() + 1) };
			}
		}

		return new String[] { IThemeManager.DEFAULT_THEME, property };
    }

    private ThemeElementHelper() {
    }
}
