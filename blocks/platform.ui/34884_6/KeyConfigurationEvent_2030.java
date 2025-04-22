
package org.eclipse.ui.commands;

import java.util.Collection;

@Deprecated
@SuppressWarnings("all")
public interface IWorkbenchCommandSupport {

	@Deprecated
	void addHandlerSubmission(HandlerSubmission handlerSubmission);

	@Deprecated
	void addHandlerSubmissions(Collection handlerSubmissions);

	@Deprecated
	ICommandManager getCommandManager();

	@Deprecated
	void removeHandlerSubmission(HandlerSubmission handlerSubmission);

	@Deprecated
	void removeHandlerSubmissions(Collection handlerSubmissions);
}
