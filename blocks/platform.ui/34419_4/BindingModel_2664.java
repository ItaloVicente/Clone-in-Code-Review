
package org.eclipse.ui.internal.keys.model;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyBinding;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.keys.NewKeysPreferenceMessages;
import org.eclipse.ui.internal.util.Util;

public class BindingElement extends ModelElement {

	public static final String PROP_TRIGGER = "trigger"; //$NON-NLS-1$
	public static final String PROP_CONTEXT = "bindingContext"; //$NON-NLS-1$
	public static final String PROP_CATEGORY = "category"; //$NON-NLS-1$
	public static final String PROP_USER_DELTA = "userDelta"; //$NON-NLS-1$
	private static final String PROP_IMAGE = "image"; //$NON-NLS-1$
	public static final String PROP_CONFLICT = "bindingConflict"; //$NON-NLS-1$
	private TriggerSequence trigger;
	private ContextElement context;
	private String category;
	private Integer userDelta;
	private Image image;
	private Boolean conflict;

	public BindingElement(KeyController kc) {
		super(kc);
	}

	public void init(Binding b, ContextModel model) {
		setCommandInfo(b.getParameterizedCommand());
		setTrigger(b.getTriggerSequence());
		setContext((ContextElement) model.getContextIdToElement().get(
				b.getContextId()));
		setUserDelta(new Integer(b.getType()));
		setModelObject(b);
	}

	private void setCommandInfo(ParameterizedCommand bindingCommand) {
		setId(bindingCommand.getId());
		try {
			setName(bindingCommand.getName());
		} catch (NotDefinedException e) {
			setName(NewKeysPreferenceMessages.Undefined_Command);
		}
		try {
			setDescription(bindingCommand.getCommand().getDescription());
		} catch (NotDefinedException e) {
			setDescription(Util.ZERO_LENGTH_STRING);
		}
		try {
			setCategory(bindingCommand.getCommand().getCategory().getName());
		} catch (NotDefinedException e) {
			setCategory(NewKeysPreferenceMessages.Unavailable_Category);
		}
		setConflict(Boolean.FALSE);
	}

	public void init(ParameterizedCommand cmd) {
		setCommandInfo(cmd);
		setTrigger(null);
		setContext(null);
		setUserDelta(new Integer(Binding.SYSTEM));

		setModelObject(cmd);
	}

	public TriggerSequence getTrigger() {
		return trigger;
	}

	public void setTrigger(TriggerSequence trigger) {
		Object old = this.trigger;
		this.trigger = trigger;
		controller.firePropertyChange(this, PROP_TRIGGER, old, trigger);
	}

	public ContextElement getContext() {
		return context;
	}

	public void setContext(ContextElement context) {
		Object old = this.context;
		this.context = context;
		controller.firePropertyChange(this, PROP_CONTEXT, old, context);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		Object old = this.category;
		this.category = category;
		controller.firePropertyChange(this, PROP_CATEGORY, old, category);
	}

	public Integer getUserDelta() {
		return userDelta;
	}

	public void setUserDelta(Integer userDelta) {
		Object old = this.userDelta;
		this.userDelta = userDelta;
		controller.firePropertyChange(this, PROP_USER_DELTA, old, userDelta);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		Object old = this.image;
		this.image = image;
		controller.firePropertyChange(this, PROP_IMAGE, old, image);
	}

	public Boolean getConflict() {
		return conflict;
	}

	public void setConflict(Boolean conflict) {
		Object old = this.conflict;
		this.conflict = conflict;
		controller.firePropertyChange(this, PROP_CONFLICT, old, conflict);
	}

	public void fill(KeyBinding binding, ContextModel contextModel) {
		setCommandInfo(binding.getParameterizedCommand());
		setTrigger(binding.getTriggerSequence());
		setContext((ContextElement) contextModel.getContextIdToElement().get(
				binding.getContextId()));
		setUserDelta(new Integer(binding.getType()));
		setModelObject(binding);
	}

	public void fill(ParameterizedCommand parameterizedCommand) {
		setCommandInfo(parameterizedCommand);
		setTrigger(null);
		setContext(null);
		setUserDelta(new Integer(Binding.SYSTEM));
		setModelObject(parameterizedCommand);
	}
}
