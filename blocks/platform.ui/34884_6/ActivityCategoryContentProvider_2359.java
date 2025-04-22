package org.eclipse.ui.internal.activities.ws;

import org.eclipse.ui.activities.ITriggerPoint;

public abstract class AbstractTriggerPoint implements ITriggerPoint {

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ITriggerPoint) {
			return getId().equals(((ITriggerPoint)obj).getId());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
