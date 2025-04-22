
package org.eclipse.ui.internal.handlers;

public interface IActionCommandMappingService {

	public String getCommandId(String actionId);

	public void map(String actionId, String commandId);
	
	public String getGeneratedCommandId(String targetId, String actionId);
}

