
package org.eclipse.ui;

import java.util.EventObject;


public class SaveablesLifecycleEvent extends EventObject {

	private static final long serialVersionUID = -3530773637989046452L;

	public static final int POST_OPEN = 1;

	public static final int PRE_CLOSE = 2;

	public static final int POST_CLOSE = 3;

	public static final int DIRTY_CHANGED = 4;

	private int eventType;

	private Saveable[] saveables;

	private boolean force;

	private boolean veto = false;

	public SaveablesLifecycleEvent(Object source, int eventType,
			Saveable[] saveables, boolean force) {
		super(source);
		this.eventType = eventType;
		this.saveables = saveables;
		this.force = force;
	}

	public int getEventType() {
		return eventType;
	}

	public Saveable[] getSaveables() {
		return saveables;
	}

	public boolean isVeto() {
		return veto;
	}

	public void setVeto(boolean veto) {
		this.veto = veto;
	}

	public boolean isForce() {
		return force;
	}

}
