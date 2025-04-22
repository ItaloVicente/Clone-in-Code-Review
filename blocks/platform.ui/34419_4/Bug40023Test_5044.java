
package org.eclipse.ui.tests.keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug36537Test extends UITestCase {

	public Bug36537Test(String name) {
		super(name);
	}

	public void testForRedundantKeySequenceBindings() {
		final IWorkbenchWindow window = openTestWindow();
		final IWorkbench workbench = window.getWorkbench();
		final IBindingService bindingService = (IBindingService) workbench
				.getAdapter(IBindingService.class);
		final Binding[] bindings = bindingService.getBindings();
		final int bindingCount = bindings.length;
		Map keySequenceBindingsByKeySequence = new HashMap();

		for (int i = 0; i < bindingCount; i++) {
			final Binding binding = bindings[i];

			TriggerSequence triggerSequence = binding.getTriggerSequence();
			List matches = (List) keySequenceBindingsByKeySequence
					.get(triggerSequence);
			if (matches == null) {
				matches = new ArrayList();
				keySequenceBindingsByKeySequence.put(triggerSequence, matches);
			}

			Iterator matchItr = matches.iterator();
			while (matchItr.hasNext()) {
				final Binding matchedBinding = (Binding) matchItr.next();
				ParameterizedCommand commandA = binding
						.getParameterizedCommand();
				ParameterizedCommand commandB = matchedBinding
						.getParameterizedCommand();
				String contextA = binding.getContextId();
				String contextB = matchedBinding.getContextId();
				String keyConfA = binding.getSchemeId();
				String keyConfB = matchedBinding.getSchemeId();
				String localeA = binding.getLocale();
				String localeB = matchedBinding.getLocale();
				String platformA = binding.getPlatform();
				String platformB = matchedBinding.getPlatform();

				boolean same = true;
				int nullMatches = 0;
				same &= (commandA == null) ? (commandB == null) : (commandA
						.equals(commandB));
				same &= (contextA == null) || (contextB == null)
						|| (contextA.equals(contextB));
				if (((contextA == null) || (contextB == null))
						&& (contextA != contextB)) {
					nullMatches++;
				}
				same &= (keyConfA == null) || (keyConfB == null)
						|| (keyConfA.equals(keyConfB));
				if (((keyConfA == null) || (keyConfB == null))
						&& (keyConfA != keyConfB)) {
					nullMatches++;
				}
				same &= (localeA == null) || (localeB == null)
						|| (localeA.equals(localeB));
				if (((localeA == null) || (localeB == null))
						&& (localeA != localeB)) {
					nullMatches++;
				}
				same &= (platformA == null) || (platformB == null)
						|| (platformA.equals(platformB));
				if (((platformA == null) || (platformB == null))
						&& (platformA != platformB)) {
					nullMatches++;
				}

				assertFalse(
						"Redundant key bindings: " + binding + ", " + matchedBinding, same && (nullMatches < 1)); //$NON-NLS-1$ //$NON-NLS-2$
			}

			matches.add(binding);
		}
	}
}
