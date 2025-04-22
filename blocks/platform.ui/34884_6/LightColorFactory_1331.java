package org.eclipse.ui.internal.themes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public interface IThemeRegistry {

    public static class HierarchyComparator implements Comparator {

        private IHierarchalThemeElementDefinition[] definitions;

        public HierarchyComparator(
                IHierarchalThemeElementDefinition[] definitions) {
            this.definitions = definitions;
        }

        @Override
		public int compare(Object arg0, Object arg1) {
            String def0 = arg0 == null ? null
                    : ((IHierarchalThemeElementDefinition) arg0)
                            .getDefaultsTo();
            String def1 = arg1 == null ? null
                    : ((IHierarchalThemeElementDefinition) arg1)
                            .getDefaultsTo();

            if (def0 == null && def1 == null) {
				return 0;
			}

            if (def0 == null) {
				return -1;
			}

            if (def1 == null) {
				return 1;
			}

            return compare(getDefaultsTo(def0), getDefaultsTo(def1));
        }

        private IHierarchalThemeElementDefinition getDefaultsTo(String id) {
            int idx = Arrays.binarySearch(definitions, id, ID_COMPARATOR);
            if (idx >= 0) {
				return definitions[idx];
			}
            return null;
        }
    }

    public static final Comparator ID_COMPARATOR = new Comparator() {

        @Override
		public int compare(Object arg0, Object arg1) {
            String str0 = getCompareString(arg0);
            String str1 = getCompareString(arg1);
            return str0.compareTo(str1);
        }

        private String getCompareString(Object object) {
            if (object instanceof String) {
				return (String) object;
			} else if (object instanceof IThemeElementDefinition) {
				return ((IThemeElementDefinition) object).getId();
			}
            return ""; //$NON-NLS-1$
        }
    };

    public ThemeElementCategory findCategory(String id);

    public ColorDefinition findColor(String id);

    public FontDefinition findFont(String id);

    public IThemeDescriptor findTheme(String id);

    public ThemeElementCategory[] getCategories();

    public ColorDefinition[] getColors();

    public ColorDefinition[] getColorsFor(String themeId);

    public FontDefinition[] getFontsFor(String themeId);

    public FontDefinition[] getFonts();

    public IThemeDescriptor[] getThemes();

    public Map getData();

    public Set getPresentationsBindingsFor(ThemeElementCategory category);
}
