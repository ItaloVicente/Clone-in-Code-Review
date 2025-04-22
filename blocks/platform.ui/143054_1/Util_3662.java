package org.eclipse.urischeme.internal.registration;

import org.eclipse.urischeme.ISchemeInformation;

public class SchemeInformation implements ISchemeInformation {

	private String name;
	private String description;
	private boolean handled;
	private String handlerInstanceLocation;

	@SuppressWarnings("javadoc")
	public SchemeInformation(String schemeName, String schemeDescription) {
		super();
		this.name = schemeName;
		this.description = schemeDescription;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isHandled() {
		return handled;
	}

	@Override
	public String getHandlerInstanceLocation() {
		return handlerInstanceLocation;
	}

	public void setHandled(boolean handled) {
		this.handled = handled;
	}

	@SuppressWarnings("javadoc")
	public void setHandlerLocation(String handlerInstanceLocation) {
		this.handlerInstanceLocation = handlerInstanceLocation;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
