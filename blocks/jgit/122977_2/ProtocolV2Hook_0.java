package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LsRefsV2Request {
	private final List<String> refPrefixes = new ArrayList<>();

	private boolean symrefs;

	private boolean peel;

	public void addRefPrefix(String prefix) {
		refPrefixes.add(prefix);
	}

	public void setSymrefs() {
		symrefs = true;
	}

	public void setPeel() {
		peel = true;
	}

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
