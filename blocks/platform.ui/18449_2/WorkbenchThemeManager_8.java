
package org.eclipse.ui.internal.themes;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.eclipse.e4.ui.internal.css.swt.CSSActivator;
import org.eclipse.e4.ui.internal.css.swt.definition.IThemeElementDefinitionOverridable;
import org.eclipse.e4.ui.internal.css.swt.definition.IThemesExtension;

@SuppressWarnings("restriction")
public class ThemesExtension implements IThemesExtension {
	private final static String DEFAULT_CATEGORY_ID = "org.eclipse.ui.themes.CssTheme"; //$NON-NLS-1$

	private String description;

	private List<IThemeElementDefinitionOverridable<?>> definitions = new ArrayList<IThemeElementDefinitionOverridable<?>>();

	public void addFontDefinition(String symbolicName) {
		FontDefinition definition = new FontDefinition(formatDefaultName(FontDefinition.class,
				symbolicName), symbolicName, null, null, DEFAULT_CATEGORY_ID, true,
				getDefaultDescription());
		definition.setAddedByCss(true);
		definitions.add(definition);
	}

	public void addColorDefinition(String symbolicName) {
		ColorDefinition definition = new ColorDefinition(formatDefaultName(ColorDefinition.class,
				symbolicName), symbolicName, null, null, DEFAULT_CATEGORY_ID, true,
				getDefaultDescription(), CSSActivator.getDefault().getBundle().getSymbolicName());
		definition.setAddedByCss(true);
		definitions.add(definition);
	}

	public List<IThemeElementDefinitionOverridable<?>> getDefinitions() {
		return definitions;
	}

	private String formatDefaultName(Class<?> cls, String symbolicName) {
		return String.format("%s #%s", cls.getSimpleName(), symbolicName); //$NON-NLS-1$
	}

	private String getDefaultDescription() {
		if (description == null) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(Theme.class.getName());
			description = resourceBundle.getString("Added.by.css.desc"); //$NON-NLS-1$
		}
		return description;
	}
}
