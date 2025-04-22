package org.eclipse.Bug545615Example;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

public class ExampleMarkerCreator extends AbstractHandler {
	private static final int COUNT = 5;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			createMarkers();
		} catch (Exception e) {
			throw new ExecutionException("Failed to create markers.", e);
		}
		return null;
	}

	private void createMarkers() throws Exception {
		int places = 1 + (int) Math.ceil(Math.log10(COUNT));
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		for (int i = 0; i < COUNT; i++) {
			IMarker m = wsRoot.createMarker("Bug545615Example.testmarker");
			m.setAttribute("number", i);
			m.setAttribute(IMarker.MESSAGE, String.format("Test%1$" + places + "d", i));
			int severity;
			if (i < COUNT / 3) {
				severity = IMarker.SEVERITY_ERROR;
			} else if (i < (COUNT / 3) * 2) {
				severity = IMarker.SEVERITY_WARNING;
			} else {
				severity = IMarker.SEVERITY_INFO;
			}
			m.setAttribute(IMarker.SEVERITY, severity);
		}
	}
}
