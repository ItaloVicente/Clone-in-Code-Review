package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_FILTER;

import java.io.EOFException;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.transport.parser.FirstWant;
import org.eclipse.jgit.lib.ObjectId;

final class ProtocolV0Parser {

	private final TransferConfig transferConfig;

	ProtocolV0Parser(TransferConfig transferConfig) {
		this.transferConfig = transferConfig;
	}

	FetchV0Request recvWants(PacketLineIn pckIn)
			throws PackProtocolException
		FetchV0Request.Builder reqBuilder = new FetchV0Request.Builder();

		boolean isFirst = true;
		boolean filterReceived = false;

		for (;;) {
			String line;
			try {
				line = pckIn.readString();
			} catch (EOFException eof) {
				if (isFirst) {
					break;
				}
				throw eof;
			}

			if (line == PacketLineIn.END) {
				break;
			}

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
			}

			if (isFirst) {
				if (line.length() > 45) {
					FirstWant firstLine = FirstWant.fromLine(line);
					reqBuilder.addClientCapabilities(firstLine.getCapabilities());
					line = firstLine.getLine();
				}
			}

			reqBuilder.addWantId(ObjectId.fromString(line.substring(5)));
			isFirst = false;
		}

		return reqBuilder.build();
	}

}
