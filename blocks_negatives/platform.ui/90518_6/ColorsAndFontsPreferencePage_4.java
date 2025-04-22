            ThemeElementCategory[] categories = ((IThemeRegistry) inputElement)
                    .getCategories();
            for (int i = 0; i < categories.length; i++) {
                if (categories[i].getParentId() == null) {
                    Set bindings = themeRegistry
                            .getPresentationsBindingsFor(categories[i]);
