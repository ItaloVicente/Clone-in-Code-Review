package org.eclipse.e4.ui.internal.dialogs.about;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AboutItem {
	private final String text;

	private final List<HyperlinkRange> linkRanges = new ArrayList<>();

	private final List<String> hrefs;

	public AboutItem(String text, List<HyperlinkRange> linkRanges, List<String> links) {
		this.text = text;
		this.linkRanges.addAll(linkRanges);
		this.hrefs = links;
	}

	public List<HyperlinkRange> getLinkRanges() {
		return linkRanges;
	}

	public String getText() {
		return text;
	}

	public boolean isLinkAt(int offset) {
		Optional<HyperlinkRange> potentialMatch = linkRanges.stream().filter(r -> r.contains(offset)).findAny();
		return potentialMatch.isPresent();
	}

	public Optional<String> getLinkAt(int offset) {
		for (int i = 0; i < linkRanges.size(); i++) {
			if (linkRanges.get(i).contains(offset)) {
				return Optional.of(hrefs.get(i));
			}
		}
		return Optional.empty();
	}
}
