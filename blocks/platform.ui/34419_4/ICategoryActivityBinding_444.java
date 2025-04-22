
package org.eclipse.ui.activities;

import java.util.Set;

public interface ICategory extends Comparable {

    void addCategoryListener(ICategoryListener categoryListener);

    Set getCategoryActivityBindings();

    String getId();

    String getName() throws NotDefinedException;

    String getDescription() throws NotDefinedException;

    boolean isDefined();

    void removeCategoryListener(ICategoryListener categoryListener);
}
