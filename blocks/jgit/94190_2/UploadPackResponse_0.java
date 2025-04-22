
package org.eclipse.jgit.transport;

import java.io.EOFException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.transport.UploadPack.FirstLine;

class UploadPackRequest {
	private final PacketLineIn pckIn;
	private final boolean biDirectionalPipe;

	final Set<String> options;

	final Set<ObjectId> wantIds;

	final Set<ObjectId> clientShallowCommits;

	final int depth;

	private UploadPackRequest(
			PacketLineIn pckIn
			Set<String> options
			Set<ObjectId> clientShallowCommits
		this.pckIn = pckIn;
		this.biDirectionalPipe = biDirectionalPipe;
		this.options = options;
		this.wantIds = wantIds;
		this.clientShallowCommits = clientShallowCommits;
		this.depth = depth;
	}

	static UploadPackRequest parseWants(
			PacketLineIn pckIn
		Set<String> options = Collections.emptySet();
		Set<ObjectId> wantIds = new HashSet<>();
		Set<ObjectId> clientShallowCommits = new HashSet<>();
		int depth = 0;

		boolean isFirst = true;
		for (;;) {
			String line;
			try {
				line = pckIn.readString();
			} catch (EOFException eof) {
				if (isFirst)
					break;
				throw eof;
			}

			if (line == PacketLineIn.END)
				break;

				depth = Integer.parseInt(line.substring(7));
				if (depth <= 0) {
					throw new PackProtocolException(MessageFormat.format(
							JGitText.get().invalidDepth
							Integer.valueOf(depth)));
				}
				continue;
			}

				clientShallowCommits.add(
						ObjectId.fromString(line.substring(8)));
				continue;
			}

				throw new PackProtocolException(MessageFormat.format(
						JGitText.get().expectedGot

			if (isFirst) {
				if (line.length() > 45) {
					FirstLine firstLine = new FirstLine(line);
					options = firstLine.getOptions();
					line = firstLine.getLine();
				} else
					options = Collections.emptySet();
			}

			wantIds.add(ObjectId.fromString(line.substring(5)));
			isFirst = false;
		}

		return new UploadPackRequest(pckIn
				clientShallowCommits
	}

	enum NegotiateState {
		RECEIVE_END
		RECEIVE_DONE
		NO_NEGOTIATION
	}

	NegotiateState parseNegotiateRequest(List<ObjectId> peerHas)
			throws IOException {
		for (;;) {
			String line;
			try {
				line = pckIn.readString();
			} catch (EOFException e) {
				if (!biDirectionalPipe && depth > 0)
					return NegotiateState.NO_NEGOTIATION;
				throw e;
			}

			if (line == PacketLineIn.END) {
				return NegotiateState.RECEIVE_END;

				peerHas.add(ObjectId.fromString(line.substring(5)));

				return NegotiateState.RECEIVE_DONE;

			} else {
				throw new PackProtocolException(MessageFormat.format(
						JGitText.get().expectedGot
			}
		}
	}
}
