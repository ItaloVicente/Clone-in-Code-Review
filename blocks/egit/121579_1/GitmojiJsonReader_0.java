
package org.eclipse.egit.core.internal.gitmoji;

import java.util.Objects;

public final class Gitmoji {

	private final String emoji;

	private final String code;

	private final String description;

	Gitmoji(String emoji, String code, String description) {
		this.emoji = Objects.requireNonNull(emoji);
		this.code = Objects.requireNonNull(code);
		this.description = Objects.requireNonNull(description);
	}

	public String getEmoji() {
		return emoji;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return emoji + ' ' + description;
	}

}
