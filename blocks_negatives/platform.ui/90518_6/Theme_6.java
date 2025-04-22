                            IPreferenceStore store = WorkbenchPlugin
                                    .getDefault().getPreferenceStore();
                            if (store.isDefault(ThemeElementHelper
                                    .createPreferenceKey(Theme.this, defs[i]
                                            .getId()))) {
                                getColorRegistry().put(defs[i].getId(), rgb);
                                processDefaultsTo(defs[i].getId(), rgb);
