
package org.eclipse.ui.activities;

import java.util.Set;

import org.eclipse.core.expressions.Expression;

public interface IActivity extends Comparable {

    void addActivityListener(IActivityListener activityListener);

    Set getActivityRequirementBindings();

    Set getActivityPatternBindings();

    String getId();

    String getName() throws NotDefinedException;

    String getDescription() throws NotDefinedException;

    boolean isDefined();

    boolean isEnabled();
    
    boolean isDefaultEnabled() throws NotDefinedException;

    void removeActivityListener(IActivityListener activityListener);
    
    Expression getExpression();
}
