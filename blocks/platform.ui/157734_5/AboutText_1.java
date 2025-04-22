package org.eclipse.e4.ui.internal.dialogs.about;

import java.util.List;
import java.util.Optional;

public class AboutItem {
	private final String text;

	private final List<HyperlinkRange> linkRanges;

	private final List<String> hrefs;

	public AboutItem(String text, List<HyperlinkRange> linkRanges, List<String> links) {
		this.text = text;
		this.linkRanges = linkRanges;
		this.hrefs = links;
	}

	public List<HyperlinkRange> getLinkRanges() {
		return linkRanges;
	}

	public String getText() {
		return text;
	}

	public boolean isLinkAt(int offset) {
		Optional<HyperlinkRange> potentialMatch = linkRanges.stream().filter(r -> r.isOffsetInRange(offset)).findAny();
		return potentialMatch.isPresent();
	}

	public String getLinkAt(int offset) {
		for (int i = 0; i < linkRanges.size(); i++) {
			if (linkRanges.get(i).isOffsetInRange(offset)) {
				return hrefs.get(i);
			}
		}
		return null;
	}
}
