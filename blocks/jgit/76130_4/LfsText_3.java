package org.eclipse.jgit.lfs.errors;

import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;

import org.eclipse.jgit.lfs.internal.LfsText;

public class CorruptMediaFile extends IOException {
	private static final long serialVersionUID = 1L;

	private Path mediaFile;

	private long expectedSize;

	private long observedSize;

	@SuppressWarnings("boxing")
	public CorruptMediaFile(Path mediaFile
			long observedSize) {
		super(MessageFormat.format(LfsText.get().inconsistentMediafileLength
				mediaFile
		this.mediaFile = mediaFile;
		this.expectedSize = expectedSize;
		this.observedSize = observedSize;
	}

	public Path getMediaFile() {
		return mediaFile;
	}

	public long getExpectedSize() {
		return expectedSize;
	}

	public long getObservedSize() {
		return observedSize;
	}
}
