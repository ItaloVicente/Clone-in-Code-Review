package org.eclipse.urischeme;

import org.eclipse.urischeme.internal.registration.SchemeInformation;

public interface ISchemeInformation {

	String getScheme();

	String getDescription();

	boolean isHandled();

	String getHandlerInstanceLocation();

	void setHandlerLocation(String location);

	static ISchemeInformation getInstance(String schemeName, String schemeDescription, String handlerLocation) {
		return new SchemeInformation(schemeName, schemeDescription, handlerLocation);
	}
}
