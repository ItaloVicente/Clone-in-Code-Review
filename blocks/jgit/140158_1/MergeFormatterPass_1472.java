
package org.eclipse.jgit.merge;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.RawText;

public class MergeFormatter {
	@Deprecated
	public void formatMerge(OutputStream out
			List<String> seqName
		formatMerge(out
	}

	public void formatMerge(OutputStream out
			List<String> seqName
		new MergeFormatterPass(out
	}

	@Deprecated
	public void formatMerge(OutputStream out
			String oursName
		formatMerge(out
				Charset.forName(charsetName));
	}

	@SuppressWarnings("unchecked")
	public void formatMerge(OutputStream out
			String oursName
			throws IOException {
		List<String> names = new ArrayList<>(3);
		names.add(baseName);
		names.add(oursName);
		names.add(theirsName);
		formatMerge(out
	}
}
