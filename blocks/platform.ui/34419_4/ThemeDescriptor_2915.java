package org.eclipse.ui.internal.themes;

import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.DataFormatException;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.ui.themes.IThemeManager;

public class Theme extends EventManager implements ITheme {

    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(Theme.class.getName());

    private CascadingColorRegistry themeColorRegistry;

    private CascadingFontRegistry themeFontRegistry;

    private IThemeDescriptor descriptor;

    private IPropertyChangeListener themeListener;

    private CascadingMap dataMap;

    private ThemeRegistry themeRegistry;

    private IPropertyChangeListener propertyListener;

    public Theme(IThemeDescriptor descriptor) {
        themeRegistry = ((ThemeRegistry) WorkbenchPlugin.getDefault()
                .getThemeRegistry());
        this.descriptor = descriptor;
        IWorkbench workbench = PlatformUI.getWorkbench();
        if (descriptor != null) {
        	ITheme defaultTheme = workbench.getThemeManager().getTheme(
                    IThemeManager.DEFAULT_THEME);
        	
            ColorDefinition[] colorDefinitions = this.descriptor.getColors();
            themeColorRegistry = new CascadingColorRegistry(defaultTheme
                    .getColorRegistry());
            if (colorDefinitions.length > 0) {
                ThemeElementHelper.populateRegistry(this, colorDefinitions,
                		PrefUtil.getInternalPreferenceStore());
            }

            FontDefinition[] fontDefinitions = this.descriptor.getFonts();
            themeFontRegistry = new CascadingFontRegistry(defaultTheme
                    .getFontRegistry());
            if (fontDefinitions.length > 0) {
                ThemeElementHelper.populateRegistry(this, fontDefinitions,
                		PrefUtil.getInternalPreferenceStore());
            }

            dataMap = new CascadingMap(((ThemeRegistry) WorkbenchPlugin
                    .getDefault().getThemeRegistry()).getData(), descriptor
                    .getData());
        }

        getColorRegistry().addListener(getCascadeListener());
        getFontRegistry().addListener(getCascadeListener());
        PrefUtil.getInternalPreferenceStore().addPropertyChangeListener(
                getPropertyListener());
    }

    private IPropertyChangeListener getPropertyListener() {
        if (propertyListener == null) {
            propertyListener = new IPropertyChangeListener() {

                @Override
				public void propertyChange(PropertyChangeEvent event) {
                    String[] split = ThemeElementHelper.splitPropertyName(
                            Theme.this, event.getProperty());
                    String key = split[1];
                    String theme = split[0];
                    if (key.equals(IWorkbenchPreferenceConstants.CURRENT_THEME_ID)) {
						return;
					}
                    try {
                    	String thisTheme = getId();
                                          
                        if (Util.equals(thisTheme, theme)) {
							if (getFontRegistry().hasValueFor(key)) {
								FontData[] data = PreferenceConverter
										.basicGetFontData((String) event
												.getNewValue());
								
								getFontRegistry().put(key, data);
								processDefaultsTo(key, data);
								return;
							}
							else if (getColorRegistry().hasValueFor(key)) {
								RGB rgb = StringConverter.asRGB((String) event
										.getNewValue());
								getColorRegistry().put(key, rgb);
								processDefaultsTo(key, rgb);
								return;
							}
						}                        
                    } catch (DataFormatException e) {
                    }
                }

                private void processDefaultsTo(String key, FontData[] fd) {
                    FontDefinition[] defs = WorkbenchPlugin.getDefault()
                            .getThemeRegistry().getFontsFor(getId());
                    for (int i = 0; i < defs.length; i++) {
                        String defaultsTo = defs[i].getDefaultsTo();
                        if (defaultsTo != null && defaultsTo.equals(key)) {
                            IPreferenceStore store = WorkbenchPlugin
                                    .getDefault().getPreferenceStore();
                            if (store.isDefault(ThemeElementHelper
                                    .createPreferenceKey(Theme.this, defs[i]
                                            .getId()))) {
                                getFontRegistry().put(defs[i].getId(), fd);
                                processDefaultsTo(defs[i].getId(), fd);
                            }
                        }
                    }
                }

                private void processDefaultsTo(String key, RGB rgb) {
                    ColorDefinition[] defs = WorkbenchPlugin.getDefault()
                            .getThemeRegistry().getColorsFor(getId());
                    for (int i = 0; i < defs.length; i++) {
                        String defaultsTo = defs[i].getDefaultsTo();
                        if (defaultsTo != null && defaultsTo.equals(key)) {
                            IPreferenceStore store = WorkbenchPlugin
                                    .getDefault().getPreferenceStore();
                            if (store.isDefault(ThemeElementHelper
                                    .createPreferenceKey(Theme.this, defs[i]
                                            .getId()))) {
                                getColorRegistry().put(defs[i].getId(), rgb);
                                processDefaultsTo(defs[i].getId(), rgb);
                            }
                        }
                    }
                }
            };
        }
        return propertyListener;
    }

    private IPropertyChangeListener getCascadeListener() {
        if (themeListener == null) {
            themeListener = new IPropertyChangeListener() {

                @Override
				public void propertyChange(PropertyChangeEvent event) {
                    firePropertyChange(event);
                }
            };
        }
        return themeListener;
    }

    @Override
	public ColorRegistry getColorRegistry() {
		if (themeColorRegistry != null) {
			return themeColorRegistry;
		}

		return WorkbenchThemeManager.getInstance()
				.getDefaultThemeColorRegistry();
	}

    @Override
	public FontRegistry getFontRegistry() {
		if (themeFontRegistry != null) {
			return themeFontRegistry;
		}

		return WorkbenchThemeManager.getInstance()
				.getDefaultThemeFontRegistry();
	}

    @Override
	public void dispose() {
        if (themeColorRegistry != null) {
            themeColorRegistry.removeListener(themeListener);
            themeColorRegistry.dispose();
        }
        if (themeFontRegistry != null) {
            themeFontRegistry.removeListener(themeListener);
            themeFontRegistry.dispose();
        }
        PrefUtil.getInternalPreferenceStore()
                .removePropertyChangeListener(getPropertyListener());
    }

    @Override
	public String getId() {
        return descriptor == null ? IThemeManager.DEFAULT_THEME : descriptor
                .getId();
    }

    @Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
        addListenerObject(listener);
    }

    @Override
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
        removeListenerObject(listener);
    }

    private void firePropertyChange(PropertyChangeEvent event) {
        Object[] listeners = getListeners();
        for (int i = 0; i < listeners.length; i++) {
            ((IPropertyChangeListener) listeners[i]).propertyChange(event);
        }
    }

    @Override
	public String getLabel() {
        return descriptor == null ? RESOURCE_BUNDLE
                .getString("DefaultTheme.label") : descriptor.getName(); //$NON-NLS-1$ 
    }

    @Override
	public String getString(String key) {
        if (dataMap != null) {
			return (String) dataMap.get(key);
		}
        return (String) themeRegistry.getData().get(key);
    }

    @Override
	public Set keySet() {
        if (dataMap != null) {
			return dataMap.keySet();
		}

        return themeRegistry.getData().keySet();
    }

    @Override
	public int getInt(String key) {
        String string = getString(key);
        if (string == null) {
			return 0;
		}
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
	public boolean getBoolean(String key) {
        String string = getString(key);
        if (string == null) {
			return false;
		}

        return Boolean.valueOf(getString(key)).booleanValue();
    }
}
