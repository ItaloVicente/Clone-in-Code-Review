package org.eclipse.ui.tests.keys;

import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.Trigger;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.util.Util;

final class TestBinding extends Binding {

	static final class TestTriggerSequence extends TriggerSequence {

		public TestTriggerSequence() {
			super(new Trigger[0]);
		}

		@Override
		public final String format() {
			return toString();
		}

		@Override
		public TriggerSequence[] getPrefixes() {
			return new TriggerSequence[0];
		}
	}

	private static final CommandManager commandManager = new CommandManager();

	static final TriggerSequence TRIGGER_SEQUENCE = new TestTriggerSequence();

	TestBinding(final String commandId, final String schemeId,
			final String contextId, final String locale, final String platform,
			final int type, final Parameterization[] parameterizations) {
		super((commandId == null) ? null : new ParameterizedCommand(
				commandManager.getCommand(commandId), parameterizations),
				schemeId, contextId, locale, platform, null, type);
	}

	@Override
	public final TriggerSequence getTriggerSequence() {
		return TRIGGER_SEQUENCE;
	}

	@Override
	public final String toString() {
		return Util.ZERO_LENGTH_STRING;
	}
}
