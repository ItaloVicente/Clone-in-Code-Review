package org.eclipse.e4.ui.internal.dialogs.about;

public class AboutItem {
	private String text;

	private int[][] linkRanges;

	private String[] hrefs;

	public AboutItem(String text, int[][] linkRanges, String[] hrefs) {
		this.text = text;
		this.linkRanges = linkRanges;
		this.hrefs = hrefs;
	}

	public int[][] getLinkRanges() {
		return linkRanges;
	}

	public String getText() {
		return text;
	}

	public boolean isLinkAt(int offset) {
		for (int[] linkRange : linkRanges) {
			if (offset >= linkRange[0] && offset < linkRange[0] + linkRange[1]) {
				return true;
			}
		}
		return false;
	}

	public String getLinkAt(int offset) {
		for (int i = 0; i < linkRanges.length; i++) {
			if (offset >= linkRanges[i][0] && offset < linkRanges[i][0] + linkRanges[i][1]) {
				return hrefs[i];
			}
		}
		return null;
	}
}
