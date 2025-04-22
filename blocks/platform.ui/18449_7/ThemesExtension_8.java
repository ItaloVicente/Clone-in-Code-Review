
package org.eclipse.ui.internal.themes;

import java.util.ResourceBundle;

public class ThemeElementDefinition {
	private String id;

	private String label;

	private String description;

	private String categoryId;

	private boolean overridden;

	private boolean addedByCss;

	private String overriddenLabel;

	public ThemeElementDefinition(String id, String label, String description, String categoryId) {
		this.id = id;
		this.label = label;
		this.description = description;
		this.categoryId = categoryId;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return label;
	}

	public void setName(String label) {
		this.label = label;
		setOverridden(true);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		setOverridden(true);
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
		setOverridden(true);
	}

	public boolean isOverridden() {
		return overridden;
	}

	protected void setOverridden(boolean overridden) {
		this.overridden = overridden;
		if (isAddedByCss()) {
			return;
		}

		boolean hasOverriddenLabel = description.endsWith(getOverriddenLabel());
		if (overridden && !hasOverriddenLabel) {
			description += ' ' + getOverriddenLabel();
		} else if (!overridden && hasOverriddenLabel) {
			description = description.substring(0, description.length()
					- getOverriddenLabel().length() - 1);
		}
	}

	public boolean isAddedByCss() {
		return addedByCss;
	}

	public void setAddedByCss(boolean addedByCss) {
		this.addedByCss = addedByCss;
	}

	public String getOverriddenLabel() {
		if (overriddenLabel == null) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(Theme.class.getName());
			overriddenLabel = resourceBundle.getString("Overridden.by.css.label"); //$NON-NLS-1$
		}
		return overriddenLabel;
	}

	public void resetToDefaultValue() {
		setOverridden(false);
		setAddedByCss(false);
	}
}
