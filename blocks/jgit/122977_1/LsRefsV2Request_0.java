package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LsRefsV2Request {
	final List<String> refPrefixes = new ArrayList<>();

	boolean symrefs;

	boolean peel;

	public List<String> getRefPrefixes() {
		return Collections.unmodifiableList(refPrefixes);
	}

	public boolean getSymrefs() {
		return symrefs;
	}

	public boolean getPeel() {
		return peel;
	}
}
