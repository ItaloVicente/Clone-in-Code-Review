
package org.eclipse.ui.commands;

@Deprecated
@SuppressWarnings("all")
public interface ICategory extends Comparable {

	@Deprecated
    void addCategoryListener(ICategoryListener categoryListener);

	@Deprecated
    String getDescription() throws NotDefinedException;

	@Deprecated
    String getId();

	@Deprecated
    String getName() throws NotDefinedException;

	@Deprecated
    boolean isDefined();

	@Deprecated
    void removeCategoryListener(ICategoryListener categoryListener);
}
