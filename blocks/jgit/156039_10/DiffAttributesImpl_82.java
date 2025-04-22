package org.eclipse.jgit.niofs.internal;

import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.fs.attribute.DiffAttributeView;
import org.eclipse.jgit.niofs.fs.attribute.DiffAttributes;

public abstract class DiffAttributeViewImpl<P extends Path> extends AbstractBasicFileAttributeView<P>
		implements DiffAttributeView {

	public static final String DIFF = "diff";

	public DiffAttributeViewImpl(final P path) {
		super(path);
	}

	@Override
	public String name() {
		return DIFF;
	}

	@Override
	public Map<String
		final DiffAttributes attrs = readAttributes();

		return new HashMap<String
			{
				for (final String attribute : attributes) {
					checkNotEmpty("attribute"

					if (attribute.equals("*") || attribute.equals(DIFF)) {
						put(DIFF
					}

					if (attribute.equals("*")) {
						break;
					}
				}
			}
		};
	}
}
