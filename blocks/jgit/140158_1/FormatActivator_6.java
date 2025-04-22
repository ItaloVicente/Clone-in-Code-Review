
package org.eclipse.jgit.archive;

import java.beans.Statement;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.eclipse.jgit.archive.internal.ArchiveText;
import org.eclipse.jgit.util.StringUtils;

public class BaseFormat {

	protected ArchiveOutputStream applyFormatOptions(ArchiveOutputStream s
			Map<String
		for (Map.Entry<String
			try {
				new Statement(s
						new Object[] { p.getValue() }).execute();
			} catch (Exception e) {
				throw new IOException(MessageFormat.format(
						ArchiveText.get().cannotSetOption
			}
		}
		return s;
	}
}
