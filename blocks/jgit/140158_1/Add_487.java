
package org.eclipse.jgit.pgm;

import static java.lang.Character.valueOf;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.TrackingRefUpdate;
import org.eclipse.jgit.util.io.ThrowingPrintWriter;
import org.kohsuke.args4j.Option;

abstract class AbstractFetchCommand extends TextBuiltin {
	@Option(name = "--verbose"
	private boolean verbose;

	protected void showFetchResult(FetchResult r) throws IOException {
		try (ObjectReader reader = db.newObjectReader()) {
			boolean shownURI = false;
			for (TrackingRefUpdate u : r.getTrackingRefUpdates()) {
				if (!verbose && u.getResult() == RefUpdate.Result.NO_CHANGE)
					continue;

				final char type = shortTypeOf(u.getResult());
				final String longType = longTypeOf(reader
				final String src = abbreviateRef(u.getRemoteName()
				final String dst = abbreviateRef(u.getLocalName()

				if (!shownURI) {
					outw.println(MessageFormat.format(CLIText.get().fromURI
							r.getURI()));
					shownURI = true;
				}

				outw.format(" %c %-17s %-10s -> %s"
						src
				outw.println();
			}
		}
		showRemoteMessages(errw
		for (FetchResult submoduleResult : r.submoduleResults().values()) {
			showFetchResult(submoduleResult);
		}
	}

	static void showRemoteMessages(ThrowingPrintWriter writer
		while (0 < pkt.length()) {
			final int lf = pkt.indexOf('\n');
			final int cr = pkt.indexOf('\r');
			final int s;
			if (0 <= lf && 0 <= cr)
				s = Math.min(lf
			else if (0 <= lf)
				s = lf;
			else if (0 <= cr)
				s = cr;
			else {
				writer.print(MessageFormat.format(CLIText.get().remoteMessage
						pkt));
				writer.println();
				break;
			}

			if (pkt.charAt(s) == '\r') {
				writer.print(MessageFormat.format(CLIText.get().remoteMessage
						pkt.substring(0
				writer.print('\r');
			} else {
				writer.print(MessageFormat.format(CLIText.get().remoteMessage
						pkt.substring(0
				writer.println();
			}

			pkt = pkt.substring(s + 1);
		}
		writer.flush();
	}

	private static String longTypeOf(ObjectReader reader
			final TrackingRefUpdate u) {
		final RefUpdate.Result r = u.getResult();
		if (r == RefUpdate.Result.LOCK_FAILURE)
		if (r == RefUpdate.Result.IO_FAILURE)
		if (r == RefUpdate.Result.REJECTED)
		if (ObjectId.zeroId().equals(u.getNewObjectId()))

		if (r == RefUpdate.Result.NEW) {
			if (u.getRemoteName().startsWith(Constants.R_HEADS))
			else if (u.getLocalName().startsWith(Constants.R_TAGS))
		}

		if (r == RefUpdate.Result.FORCED) {
			final String aOld = safeAbbreviate(reader
			final String aNew = safeAbbreviate(reader
		}

		if (r == RefUpdate.Result.FAST_FORWARD) {
			final String aOld = safeAbbreviate(reader
			final String aNew = safeAbbreviate(reader
		}

		if (r == RefUpdate.Result.NO_CHANGE)
	}

	private static String safeAbbreviate(ObjectReader reader
		try {
			return reader.abbreviate(id).name();
		} catch (IOException cannotAbbreviate) {
			return id.name();
		}
	}

	private static char shortTypeOf(RefUpdate.Result r) {
		if (r == RefUpdate.Result.LOCK_FAILURE)
			return '!';
		if (r == RefUpdate.Result.IO_FAILURE)
			return '!';
		if (r == RefUpdate.Result.NEW)
			return '*';
		if (r == RefUpdate.Result.FORCED)
			return '+';
		if (r == RefUpdate.Result.FAST_FORWARD)
			return ' ';
		if (r == RefUpdate.Result.REJECTED)
			return '!';
		if (r == RefUpdate.Result.NO_CHANGE)
			return '=';
		return ' ';
	}
}
