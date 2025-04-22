package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_FILTER;

import java.io.EOFException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;

public final class ProtocolV1Parser {

	private final TransferConfig transferConfig;

	public static final class FirstLine {
		private final String line;

		private final Set<String> options;

		public FirstLine(String line) {
			if (line.length() > 45) {
				final HashSet<String> opts = new HashSet<>();
				String opt = line.substring(45);
					opt = opt.substring(1);
					opts.add(c);
				this.line = line.substring(0
				this.options = Collections.unmodifiableSet(opts);
			} else {
				this.line = line;
				this.options = Collections.emptySet();
			}
		}

		public String getLine() {
			return line;
		}

		public Set<String> getOptions() {
			return options;
		}
	}

	ProtocolV1Parser(TransferConfig transferConfig) {
		this.transferConfig = transferConfig;
	}

	FetchV1Request recvWants(PacketLineIn pckIn)
			throws PackProtocolException
		FetchV1Request.Builder reqBuilder = new FetchV1Request.Builder();

		boolean isFirst = true;
		boolean filterReceived = false;

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

				int depth = Integer.parseInt(line.substring(7));
				if (depth <= 0) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidDepth
									Integer.valueOf(depth)));
				}
				reqBuilder.setDepth(depth);
				continue;
			}

				reqBuilder.addClientShallowCommit(
						ObjectId.fromString(line.substring(8)));
				continue;
			}

			if (transferConfig.isAllowFilter()
				String arg = line.substring(OPTION_FILTER.length() + 1);

				if (filterReceived) {
					throw new PackProtocolException(
							JGitText.get().tooManyFilters);
				}
				filterReceived = true;

				reqBuilder.setFilterBlobLimit(ProtocolV2Parser.filterLine(arg));
				continue;
			}

				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().expectedGot

			if (isFirst) {
				if (line.length() > 45) {
					FirstLine firstLine = new FirstLine(line);
					reqBuilder.addAllOptions(firstLine.getOptions());
					line = firstLine.getLine();
				}
			}

			reqBuilder.addWantsIds(ObjectId.fromString(line.substring(5)));
			isFirst = false;
		}

		return reqBuilder.build();
	}

}
