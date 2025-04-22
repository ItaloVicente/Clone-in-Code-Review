
package org.eclipse.ui.internal.keys.model;

public class ModelElement {

	public static final String PROP_PARENT = "parent"; //$NON-NLS-1$
	public static final String PROP_ID = "id"; //$NON-NLS-1$
	public static final String PROP_NAME = "name"; //$NON-NLS-1$
	public static final String PROP_DESCRIPTION = "description"; //$NON-NLS-1$
	public static final String PROP_MODEL_OBJECT = "modelObject"; //$NON-NLS-1$
	protected KeyController controller;
	private ModelElement parent;
	private String id;
	private String name;
	private String description;
	private Object modelObject;

	public ModelElement(KeyController kc) {
		controller = kc;
	}

	public ModelElement getParent() {
		return parent;
	}

	public void setParent(ModelElement parent) {
		ModelElement old = this.parent;
		this.parent = parent;
		controller.firePropertyChange(this, PROP_PARENT, old, parent);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		String old = this.id;
		this.id = id;
		controller.firePropertyChange(this, PROP_ID, old, id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		controller.firePropertyChange(this, PROP_NAME, old, name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		String old = this.description;
		this.description = description;
		controller.firePropertyChange(this, PROP_DESCRIPTION, old, description);
	}

	public Object getModelObject() {
		return modelObject;
	}

	public void setModelObject(Object o) {
		Object old = this.modelObject;
		modelObject = o;
		controller.firePropertyChange(this, PROP_MODEL_OBJECT, old, o);
	}
}
