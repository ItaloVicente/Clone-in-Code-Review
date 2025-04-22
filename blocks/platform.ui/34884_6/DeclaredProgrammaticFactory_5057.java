
package org.eclipse.ui.tests.menus;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;

public class ContextIdValues implements IParameterValues {

	@Override
	public Map getParameterValues() {
		Map values = new HashMap();

		IContextService contextService = PlatformUI
				.getWorkbench().getService(IContextService.class);
		Context[] definedContexts = contextService.getDefinedContexts();
		try {
			for (int i = 0; i < definedContexts.length; i++) {
				values.put(definedContexts[i].getName(), definedContexts[i]
						.getId());
			}
		} catch (NotDefinedException e) {
			e.printStackTrace();
		}
		return values;
	}
}
