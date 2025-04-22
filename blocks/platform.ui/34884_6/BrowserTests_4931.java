package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.contexts.IContextIds;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.keys.IBindingService;

public final class BindingsExtensionDynamicTest extends DynamicTestCase {

	public BindingsExtensionDynamicTest(final String testName) {
		super(testName);
	}

	@Override
	protected final String getExtensionId() {
		return "bindingsExtensionDynamicTest.testDynamicBindingAddition";
	}

	@Override
	protected final String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_BINDINGS;
	}

	@Override
	protected final String getInstallLocation() {
		return "data/org.eclipse.bindingsExtensionDynamicTest";
	}

	public void testBindings() throws ParseException {
		final IBindingService bindingService = (IBindingService) getWorkbench()
				.getAdapter(IBindingService.class);
		final TriggerSequence triggerSequence = KeySequence.getInstance("M1+W");
		Binding[] bindings;
		Scheme scheme;
		boolean found;

		found = false;
		bindings = bindingService.getBindings();
		if (bindings != null) {
			for (int i = 0; i < bindings.length; i++) {
				final Binding binding = bindings[i];
				if ("monkey".equals(binding.getSchemeId())
						&& IContextIds.CONTEXT_ID_WINDOW.equals(binding
								.getContextId())
						&& "org.eclipse.ui.views.showView".equals(binding
								.getParameterizedCommand().getId())
						&& binding.getParameterizedCommand().getParameterMap()
								.containsKey(
										IWorkbenchCommandConstants.VIEWS_SHOW_VIEW_PARM_ID)
						&& binding.getPlatform() == null
						&& binding.getLocale() == null
						&& binding.getType() == Binding.SYSTEM
						&& triggerSequence.equals(binding.getTriggerSequence())) {
					found = true;

				}
			}
		}
		assertTrue(!found);
		scheme = bindingService.getScheme("monkey");
		try {
			scheme.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}

		getBundle();

		found = false;
		bindings = bindingService.getBindings();
		if (bindings != null) {
			for (int i = 0; i < bindings.length; i++) {
				final Binding binding = bindings[i];
				if ("monkey".equals(binding.getSchemeId())
						&& IContextIds.CONTEXT_ID_WINDOW.equals(binding
								.getContextId())
						&& "org.eclipse.ui.views.showView".equals(binding
								.getParameterizedCommand().getId())
						&& binding.getParameterizedCommand().getParameterMap()
								.containsKey(
										IWorkbenchCommandConstants.VIEWS_SHOW_VIEW_PARM_ID)
						&& binding.getPlatform() == null
						&& binding.getLocale() == null
						&& binding.getType() == Binding.SYSTEM
						&& triggerSequence.equals(binding.getTriggerSequence())) {
					found = true;

				}
			}
		}
		assertTrue(found);
		scheme = bindingService.getScheme("monkey");
		try {
			assertTrue("Monkey".equals(scheme.getName()));
		} catch (final NotDefinedException e) {
			fail();
		}

		removeBundle();

		found = false;
		bindings = bindingService.getBindings();
		if (bindings != null) {
			for (int i = 0; i < bindings.length; i++) {
				final Binding binding = bindings[i];
				if ("monkey".equals(binding.getSchemeId())
						&& IContextIds.CONTEXT_ID_WINDOW.equals(binding
								.getContextId())
						&& "org.eclipse.ui.views.showView".equals(binding
								.getParameterizedCommand().getId())
						&& binding.getParameterizedCommand().getParameterMap()
								.containsKey(
										IWorkbenchCommandConstants.VIEWS_SHOW_VIEW_PARM_ID)
						&& binding.getPlatform() == null
						&& binding.getLocale() == null
						&& binding.getType() == Binding.SYSTEM
						&& triggerSequence.equals(binding.getTriggerSequence())) {
					found = true;

				}
			}
		}
		assertTrue(!found);
		scheme = bindingService.getScheme("monkey");
		try {
			scheme.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
	}
}
