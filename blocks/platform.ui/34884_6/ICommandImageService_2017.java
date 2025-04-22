package org.eclipse.ui.commands;

import java.util.List;
import java.util.Map;

@Deprecated
@SuppressWarnings("all")
public interface ICommand extends Comparable {

	@Deprecated
    void addCommandListener(ICommandListener commandListener);

	@Deprecated
    Object execute(Map parameterValuesByName) throws ExecutionException,
            NotHandledException;

	@Deprecated
    Map getAttributeValuesByName() throws NotHandledException;

	@Deprecated
    String getCategoryId() throws NotDefinedException;

	@Deprecated
    String getDescription() throws NotDefinedException;

	@Deprecated
    String getId();

    List getKeySequenceBindings();

	@Deprecated
    String getName() throws NotDefinedException;

	@Deprecated
    boolean isDefined();

	@Deprecated
    boolean isHandled();

	@Deprecated
    void removeCommandListener(ICommandListener commandListener);
}
