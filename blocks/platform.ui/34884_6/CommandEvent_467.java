
package org.eclipse.ui.commands;

@Deprecated
public final class CategoryEvent {

    private final ICategory category;

    private final boolean definedChanged;

    private final boolean nameChanged;

	@Deprecated
    public CategoryEvent(ICategory category, boolean definedChanged,
            boolean nameChanged) {
        if (category == null) {
			throw new NullPointerException();
		}

        this.category = category;
        this.definedChanged = definedChanged;
        this.nameChanged = nameChanged;
    }

	@Deprecated
    public ICategory getCategory() {
        return category;
    }

	@Deprecated
    public boolean hasDefinedChanged() {
        return definedChanged;
    }

	@Deprecated
    public boolean hasNameChanged() {
        return nameChanged;
    }
}
