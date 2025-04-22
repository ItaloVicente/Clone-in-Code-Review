package org.eclipse.ui.keys;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.IBindingManagerListener;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.services.IDisposable;

public interface IBindingService extends IDisposable {

	public static final String DEFAULT_DEFAULT_ACTIVE_SCHEME_ID = "org.eclipse.ui.defaultAcceleratorConfiguration"; //$NON-NLS-1$

	public void addBindingManagerListener(IBindingManagerListener listener);

	public void removeBindingManagerListener(IBindingManagerListener listener);

	public TriggerSequence[] getActiveBindingsFor(
			ParameterizedCommand parameterizedCommand);

	public TriggerSequence[] getActiveBindingsFor(String commandId);

	public Scheme getActiveScheme();

	public TriggerSequence getBestActiveBindingFor(ParameterizedCommand command);

	public TriggerSequence getBestActiveBindingFor(String commandId);
	
	public String getBestActiveBindingFormattedFor(String commandId);

	public Binding[] getBindings();

	public TriggerSequence getBuffer();

	public String getDefaultSchemeId();

	public Scheme[] getDefinedSchemes();

	public String getLocale();

	public Map getPartialMatches(TriggerSequence trigger);

	public Binding getPerfectMatch(TriggerSequence trigger);

	public String getPlatform();

	public Scheme getScheme(String schemeId);

	public boolean isKeyFilterEnabled();

	public boolean isPartialMatch(TriggerSequence trigger);

	public boolean isPerfectMatch(TriggerSequence trigger);

	public void openKeyAssistDialog();

	public void readRegistryAndPreferences(ICommandService commandService);

	public void savePreferences(Scheme activeScheme, Binding[] bindings)
			throws IOException;

	public void setKeyFilterEnabled(boolean enabled);

	public Collection getConflictsFor(TriggerSequence sequence);
}
