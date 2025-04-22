package org.eclipse.ui;

import org.w3c.dom.DOMException;

public interface IMemento {
    public static final String TAG_ID = "IMemento.internal.id"; //$NON-NLS-1$

    public IMemento createChild(String type);

    public IMemento createChild(String type, String id);

    public IMemento getChild(String type);

	public IMemento[] getChildren();

    public IMemento[] getChildren(String type);

    public Float getFloat(String key);

	public String getType();

    public String getID();

    public Integer getInteger(String key);

    public String getString(String key);

	public Boolean getBoolean(String key);

    public String getTextData();
	
	public String[] getAttributeKeys();

    public void putFloat(String key, float value);

    public void putInteger(String key, int value);

    public void putMemento(IMemento memento);

    public void putString(String key, String value);

	public void putBoolean(String key, boolean value);

    public void putTextData(String data);
}
