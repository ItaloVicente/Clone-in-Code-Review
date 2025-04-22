package org.eclipse.jgit.mailmap;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.PersonIdent;

public class Mailmap {
	private final List<MailmapEntry> entries;

	public Mailmap() {
		this.entries = new ArrayList<>();
	}

	public Mailmap(List<MailmapEntry> entries) {
		this.entries = new ArrayList<>(entries);
	}

	public PersonIdent map(@Nullable PersonIdent ident) {
		if (ident == null) {
			return null;
		}

		for (MailmapEntry entry : entries) {
			if (entry.matches(ident)) {
				return entry.map(ident);
			}
		}
		return ident;
	}

	public void append(Mailmap mailmap) {
		entries.addAll(mailmap.entries);
	}
}
