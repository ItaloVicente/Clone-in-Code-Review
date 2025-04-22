package org.eclipse.urischeme.internal.registration;

import org.eclipse.urischeme.ISchemeInformation;

public class SchemeInformation implements ISchemeInformation {

	private String scheme;
	private boolean handled;
	private String handlerInstanceLocation;


	@SuppressWarnings("javadoc")
	public SchemeInformation(String scheme) {
		super();
		this.scheme = scheme;
	}

	@Override
	public String getScheme() {
		return scheme;
	}

	@Override
	public boolean isHandled() {
		return handled;
	}

	@Override
	public String getHandlerInstanceLocation() {
		return handlerInstanceLocation;
	}

	@SuppressWarnings("javadoc")
	public void setHandled(boolean handled) {
		this.handled = handled;
	}

	@SuppressWarnings("javadoc")
	public void setHandlerInstanceLocation(String handlerInstanceLocation) {
		this.handlerInstanceLocation = handlerInstanceLocation;
	}

}
