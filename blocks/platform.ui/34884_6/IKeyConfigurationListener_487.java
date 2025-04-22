
package org.eclipse.ui.commands;

@Deprecated
@SuppressWarnings("all")
public interface IKeyConfiguration extends Comparable {

	@Deprecated
    void addKeyConfigurationListener(
            IKeyConfigurationListener keyConfigurationListener);

	@Deprecated
    String getDescription() throws NotDefinedException;

	@Deprecated
    String getId();

	@Deprecated
    String getName() throws NotDefinedException;

	@Deprecated
    String getParentId() throws NotDefinedException;

	@Deprecated
    boolean isActive();

	@Deprecated
    boolean isDefined();

	@Deprecated
    void removeKeyConfigurationListener(
            IKeyConfigurationListener keyConfigurationListener);
}
