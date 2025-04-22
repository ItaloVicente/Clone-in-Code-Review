
package org.eclipse.ui.activities;

public final class CategoryEvent {
    private ICategory category;

    private boolean categoryActivityBindingsChanged;

    private boolean definedChanged;

    private boolean nameChanged;

    private boolean descriptionChanged;

    public CategoryEvent(ICategory category,
            boolean categoryActivityBindingsChanged, boolean definedChanged,
            boolean descriptionChanged, boolean nameChanged) {
        if (category == null) {
			throw new NullPointerException();
		}

        this.category = category;
        this.categoryActivityBindingsChanged = categoryActivityBindingsChanged;
        this.definedChanged = definedChanged;
        this.nameChanged = nameChanged;
        this.descriptionChanged = descriptionChanged;
    }

    public ICategory getCategory() {
        return category;
    }

    public boolean hasDefinedChanged() {
        return definedChanged;
    }

    public boolean hasNameChanged() {
        return nameChanged;
    }

    public boolean hasDescriptionChanged() {
        return descriptionChanged;
    }

    public boolean haveCategoryActivityBindingsChanged() {
        return categoryActivityBindingsChanged;
    }
}
