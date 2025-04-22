package org.eclipse.ui.commands;

import java.util.Map;

@Deprecated
@SuppressWarnings("all")
public interface IHandler {

	@Deprecated
    void addHandlerListener(IHandlerListener handlerListener);

	@Deprecated
    public void dispose();

	@Deprecated
    Object execute(Map parameterValuesByName) throws ExecutionException;

	@Deprecated
    Map getAttributeValuesByName();

	@Deprecated
    void removeHandlerListener(IHandlerListener handlerListener);
}
