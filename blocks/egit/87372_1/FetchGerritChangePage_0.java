package org.eclipse.egit.ui.internal.fetch;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.osgi.util.NLS;

class Change implements Comparable<Change> {
	private final String refName;

	private final Integer changeNumber;

	private final Integer patchSetNumber;

	static Change fromRef(String refName) {
		try {
			if (!refName.startsWith("refs/changes/")) //$NON-NLS-1$
				return null;
			String[] tokens = refName.substring(13).split("/"); //$NON-NLS-1$
			if (tokens.length != 3)
				return null;
			Integer changeNumber = Integer.valueOf(tokens[1]);
			Integer patchSetNumber = Integer.valueOf(tokens[2]);
			return new Change(refName, changeNumber, patchSetNumber);
		} catch (NumberFormatException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	private Change(String refName, Integer changeNumber,
			Integer patchSetNumber) {
		this.refName = refName;
		this.changeNumber = changeNumber;
		this.patchSetNumber = patchSetNumber;
	}

	public String getRefName() {
		return refName;
	}

	public Integer getChangeNumber() {
		return changeNumber;
	}

	public Integer getPatchSetNumber() {
		return patchSetNumber;
	}

	public String suggestBranchName() {
		return NLS.bind(UIText.Change_SuggestedBranchNamePattern, changeNumber,
				patchSetNumber);
	}

	public String computeFullRefName() {
		return NLS.bind(UIText.Change_FullRefNamePattern, changeNumber,
				patchSetNumber);
	}

	@Override
	public String toString() {
		return refName;
	}

	@Override
	public int compareTo(Change other) {
		int changeDiff = this.changeNumber.compareTo(other.changeNumber);
		if (changeDiff == 0) {
			changeDiff = this.patchSetNumber.compareTo(other.patchSetNumber);
		}
		return changeDiff;
	}
}
