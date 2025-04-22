package org.eclipse.ui.internal.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.core.commands.contexts.ContextManagerEvent;
import org.eclipse.core.commands.contexts.IContextManagerListener;
import org.eclipse.e4.core.commands.internal.HandlerServiceImpl;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.BindingManagerEvent;
import org.eclipse.jface.bindings.IBindingManagerListener;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.ui.commands.CommandManagerEvent;
import org.eclipse.ui.commands.ICategory;
import org.eclipse.ui.commands.ICommand;
import org.eclipse.ui.commands.ICommandManager;
import org.eclipse.ui.commands.ICommandManagerListener;
import org.eclipse.ui.commands.IKeyConfiguration;
import org.eclipse.ui.internal.handlers.LegacyHandlerWrapper;
import org.eclipse.ui.internal.keys.SchemeLegacyWrapper;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.keys.KeySequence;

public final class CommandManagerLegacyWrapper implements ICommandManager,
		org.eclipse.core.commands.ICommandManagerListener,
		IBindingManagerListener, IContextManagerListener {

	public static boolean DEBUG_COMMAND_EXECUTION = false;

	public static boolean DEBUG_HANDLERS = false;

	public static String DEBUG_HANDLERS_COMMAND_ID = null;

	static boolean validateKeySequence(KeySequence keySequence) {
		if (keySequence == null) {
			return false;
		}
		List keyStrokes = keySequence.getKeyStrokes();
		int size = keyStrokes.size();
		if (size == 0 || size > 4 || !keySequence.isComplete()) {
			return false;
		}
		return true;
	}

	private final BindingManager bindingManager;

	private final CommandManager commandManager;

	private List commandManagerListeners;

	private final ContextManager contextManager;

	public CommandManagerLegacyWrapper(final BindingManager bindingManager,
			final CommandManager commandManager,
			final ContextManager contextManager) {
		if (contextManager == null) {
			throw new NullPointerException(
					"The context manager cannot be null."); //$NON-NLS-1$
		}
		this.bindingManager = bindingManager;
		this.commandManager = commandManager;
		this.contextManager = contextManager;
	}

	@Override
	public final void addCommandManagerListener(
			final ICommandManagerListener commandManagerListener) {
		if (commandManagerListener == null) {
			throw new NullPointerException("Cannot add a null listener."); //$NON-NLS-1$
		}

		if (commandManagerListeners == null) {
			commandManagerListeners = new ArrayList();
			this.commandManager.addCommandManagerListener(this);
			this.bindingManager.addBindingManagerListener(this);
			this.contextManager.addContextManagerListener(this);
		}

		if (!commandManagerListeners.contains(commandManagerListener)) {
			commandManagerListeners.add(commandManagerListener);
		}
	}

	@Override
	public final void bindingManagerChanged(final BindingManagerEvent event) {
		final boolean schemeDefinitionsChanged = event.getScheme() != null;
		final Set previousSchemes;
		if (schemeDefinitionsChanged) {
			previousSchemes = new HashSet();
			final Scheme scheme = event.getScheme();
			final Scheme[] definedSchemes = event.getManager()
					.getDefinedSchemes();
			final int definedSchemesCount = definedSchemes.length;
			for (int i = 0; i < definedSchemesCount; i++) {
				final Scheme definedScheme = definedSchemes[0];
				if ((definedScheme == scheme) && (event.isSchemeDefined())) {
					continue; // skip this one, it was just defined.
				}
				previousSchemes.add(definedSchemes[0].getId());
			}
			if (!event.isSchemeDefined()) {
				previousSchemes.add(scheme.getId());
			}
		} else {
			previousSchemes = null;
		}

		fireCommandManagerChanged(new CommandManagerEvent(this, false, event
				.isActiveSchemeChanged(), event.isLocaleChanged(), event
				.isPlatformChanged(), false, false, schemeDefinitionsChanged,
				null, null, previousSchemes));
	}

	@Override
	public final void commandManagerChanged(
			final org.eclipse.core.commands.CommandManagerEvent event) {
		final boolean categoryIdsChanged = event.isCategoryChanged();
		final Set previousCategoryIds;
		if (categoryIdsChanged) {
			previousCategoryIds = new HashSet(commandManager
					.getDefinedCategoryIds());
			final String categoryId = event.getCategoryId();
			if (event.isCategoryDefined()) {
				previousCategoryIds.remove(categoryId);
			} else {
				previousCategoryIds.add(categoryId);
			}
		} else {
			previousCategoryIds = null;
		}

		final boolean commandIdsChanged = event.isCommandChanged();
		final Set previousCommandIds;
		if (commandIdsChanged) {
			previousCommandIds = new HashSet(commandManager
					.getDefinedCommandIds());
			final String commandId = event.getCommandId();
			if (event.isCommandDefined()) {
				previousCommandIds.remove(commandId);
			} else {
				previousCommandIds.add(commandId);
			}
		} else {
			previousCommandIds = null;
		}

		fireCommandManagerChanged(new CommandManagerEvent(this, false, false,
				false, false, categoryIdsChanged, commandIdsChanged, false,
				previousCategoryIds, previousCommandIds, null));
	}

	@Override
	public final void contextManagerChanged(final ContextManagerEvent event) {
		fireCommandManagerChanged(new CommandManagerEvent(this, event
				.isActiveContextsChanged(), false, false, false, false, false,
				false, null, null, null));
	}

	private void fireCommandManagerChanged(
			CommandManagerEvent commandManagerEvent) {
		if (commandManagerEvent == null) {
			throw new NullPointerException();
		}
		if (commandManagerListeners != null) {
			for (int i = 0; i < commandManagerListeners.size(); i++) {
				((ICommandManagerListener) commandManagerListeners.get(i))
						.commandManagerChanged(commandManagerEvent);
			}
		}
	}

	@Override
	public Set getActiveContextIds() {
		return contextManager.getActiveContextIds();
	}

	@Override
	public String getActiveKeyConfigurationId() {
		final Scheme scheme = bindingManager.getActiveScheme();
		if (scheme != null) {
			return scheme.getId();
		}

		return Util.ZERO_LENGTH_STRING;
	}

	@Override
	public String getActiveLocale() {
		return bindingManager.getLocale();
	}

	@Override
	public String getActivePlatform() {
		return bindingManager.getPlatform();
	}

	@Override
	public ICategory getCategory(String categoryId) {
		return null;
	}

	@Override
	public ICommand getCommand(String commandId) {
		final Command command = commandManager.getCommand(commandId);
		if (!command.isDefined()) {
			command.setHandler(HandlerServiceImpl.getHandler(commandId));
		}
		return new CommandLegacyWrapper(command, bindingManager);
	}

	@Override
	public Set getDefinedCategoryIds() {
		return commandManager.getDefinedCategoryIds();
	}

	@Override
	public Set getDefinedCommandIds() {
		return commandManager.getDefinedCommandIds();
	}

	@Override
	public Set getDefinedKeyConfigurationIds() {
		final Set definedIds = new HashSet();
		final Scheme[] schemes = bindingManager.getDefinedSchemes();
		for (int i = 0; i < schemes.length; i++) {
			definedIds.add(schemes[i].getId());
		}
		return definedIds;
	}

	@Override
	public IKeyConfiguration getKeyConfiguration(String keyConfigurationId) {
		final Scheme scheme = bindingManager.getScheme(keyConfigurationId);
		return new SchemeLegacyWrapper(scheme, bindingManager);
	}

	@Override
	public Map getPartialMatches(KeySequence keySequence) {
		try {
			final org.eclipse.jface.bindings.keys.KeySequence sequence = org.eclipse.jface.bindings.keys.KeySequence
					.getInstance(keySequence.toString());
			final Map partialMatches = bindingManager
					.getPartialMatches(sequence);
			final Map returnValue = new HashMap();
			final Iterator matchItr = partialMatches.entrySet().iterator();
			while (matchItr.hasNext()) {
				final Map.Entry entry = (Map.Entry) matchItr.next();
				final TriggerSequence trigger = (TriggerSequence) entry
						.getKey();
				if (trigger instanceof org.eclipse.jface.bindings.keys.KeySequence) {
					final org.eclipse.jface.bindings.keys.KeySequence triggerKey = (org.eclipse.jface.bindings.keys.KeySequence) trigger;
					returnValue.put(KeySequence.getInstance(triggerKey
							.toString()), entry.getValue());
				}
			}
			return returnValue;
		} catch (final ParseException e) {
			return new HashMap();
		} catch (final org.eclipse.ui.keys.ParseException e) {
			return new HashMap();
		}
	}

	@Override
	public String getPerfectMatch(KeySequence keySequence) {
		try {
			final org.eclipse.jface.bindings.keys.KeySequence sequence = org.eclipse.jface.bindings.keys.KeySequence
					.getInstance(keySequence.toString());
			final Binding binding = bindingManager.getPerfectMatch(sequence);
			if (binding == null) {
				return null;
			}

			return binding.getParameterizedCommand().getId();

		} catch (final ParseException e) {
			return null;
		}
	}

	@Override
	public boolean isPartialMatch(KeySequence keySequence) {
		try {
			final org.eclipse.jface.bindings.keys.KeySequence sequence = org.eclipse.jface.bindings.keys.KeySequence
					.getInstance(keySequence.toString());
			return bindingManager.isPartialMatch(sequence);
		} catch (final ParseException e) {
			return false;
		}
	}

	@Override
	public boolean isPerfectMatch(KeySequence keySequence) {
		try {
			final org.eclipse.jface.bindings.keys.KeySequence sequence = org.eclipse.jface.bindings.keys.KeySequence
					.getInstance(keySequence.toString());
			return bindingManager.isPerfectMatch(sequence);
		} catch (final ParseException e) {
			return false;
		}
	}

	@Override
	public void removeCommandManagerListener(
			ICommandManagerListener commandManagerListener) {
		if (commandManagerListener == null) {
			throw new NullPointerException("Cannot remove a null listener"); //$NON-NLS-1$
		}

		if (commandManagerListeners != null) {
			commandManagerListeners.remove(commandManagerListener);
			if (commandManagerListeners.isEmpty()) {
				commandManagerListeners = null;
				this.commandManager.removeCommandManagerListener(this);
				this.bindingManager.removeBindingManagerListener(this);
				this.contextManager.removeContextManagerListener(this);
			}
		}
	}

	public final void setHandlersByCommandId(final Map handlersByCommandId) {
		final Iterator entryItr = handlersByCommandId.entrySet().iterator();
		while (entryItr.hasNext()) {
			final Map.Entry entry = (Map.Entry) entryItr.next();
			final Object handler = entry.getValue();
			if (handler instanceof org.eclipse.ui.commands.IHandler) {
				final String commandId = (String) entry.getKey();
				handlersByCommandId.put(commandId, new LegacyHandlerWrapper(
						(org.eclipse.ui.commands.IHandler) handler));
			}
		}

		commandManager.setHandlersByCommandId(handlersByCommandId);
	}
}
