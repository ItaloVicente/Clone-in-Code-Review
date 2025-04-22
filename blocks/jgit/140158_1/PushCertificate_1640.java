package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_DEEPEN_RELATIVE;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_FILTER;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_AGENT;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_INCLUDE_TAG;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_NO_PROGRESS;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_OFS_DELTA;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_SERVER_OPTION;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_SIDE_BAND_64K;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_THIN_PACK;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_WANT_REF;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;

final class ProtocolV2Parser {

	private final TransferConfig transferConfig;

	ProtocolV2Parser(TransferConfig transferConfig) {
		this.transferConfig = transferConfig;
	}

	private static String consumeCapabilities(PacketLineIn pckIn
			Consumer<String> serverOptionConsumer
			Consumer<String> agentConsumer) throws IOException {

		String serverOptionPrefix = OPTION_SERVER_OPTION + '=';
		String agentPrefix = OPTION_AGENT + '=';

		String line = pckIn.readString();
		while (line != PacketLineIn.DELIM && line != PacketLineIn.END) {
			if (line.startsWith(serverOptionPrefix)) {
				serverOptionConsumer
						.accept(line.substring(serverOptionPrefix.length()));
			} else if (line.startsWith(agentPrefix)) {
				agentConsumer.accept(line.substring(agentPrefix.length()));
			} else {
			}
			line = pckIn.readString();
		}

		return line;
	}

	FetchV2Request parseFetchRequest(PacketLineIn pckIn)
			throws PackProtocolException
		FetchV2Request.Builder reqBuilder = FetchV2Request.builder();

		reqBuilder.addClientCapability(OPTION_SIDE_BAND_64K);

		String line = consumeCapabilities(pckIn
				serverOption -> reqBuilder.addServerOption(serverOption)
				agent -> reqBuilder.setAgent(agent));

		if (line == PacketLineIn.END) {
			return reqBuilder.build();
		}

		if (line != PacketLineIn.DELIM) {
			throw new PackProtocolException(
					MessageFormat.format(JGitText.get().unexpectedPacketLine
							line));
		}

		boolean filterReceived = false;
		while ((line = pckIn.readString()) != PacketLineIn.END) {
				reqBuilder.addWantId(ObjectId.fromString(line.substring(5)));
			} else if (transferConfig.isAllowRefInWant()
				reqBuilder.addWantedRef(line.substring(OPTION_WANT_REF.length() + 1));
				reqBuilder.addPeerHas(ObjectId.fromString(line.substring(5)));
				reqBuilder.setDoneReceived();
			} else if (line.equals(OPTION_THIN_PACK)) {
				reqBuilder.addClientCapability(OPTION_THIN_PACK);
			} else if (line.equals(OPTION_NO_PROGRESS)) {
				reqBuilder.addClientCapability(OPTION_NO_PROGRESS);
			} else if (line.equals(OPTION_INCLUDE_TAG)) {
				reqBuilder.addClientCapability(OPTION_INCLUDE_TAG);
			} else if (line.equals(OPTION_OFS_DELTA)) {
				reqBuilder.addClientCapability(OPTION_OFS_DELTA);
				reqBuilder.addClientShallowCommit(
						ObjectId.fromString(line.substring(8)));
				int parsedDepth = Integer.parseInt(line.substring(7));
				if (parsedDepth <= 0) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidDepth
									Integer.valueOf(parsedDepth)));
				}
				if (reqBuilder.getDeepenSince() != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenSinceWithDeepen);
				}
				if (reqBuilder.hasDeepenNotRefs()) {
					throw new PackProtocolException(
							JGitText.get().deepenNotWithDeepen);
				}
				reqBuilder.setDepth(parsedDepth);
				reqBuilder.addDeepenNotRef(line.substring(11));
				if (reqBuilder.getDepth() != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenNotWithDeepen);
				}
			} else if (line.equals(OPTION_DEEPEN_RELATIVE)) {
				reqBuilder.addClientCapability(OPTION_DEEPEN_RELATIVE);
				int ts = Integer.parseInt(line.substring(13));
				if (ts <= 0) {
					throw new PackProtocolException(MessageFormat
							.format(JGitText.get().invalidTimestamp
				}
				if (reqBuilder.getDepth() != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenSinceWithDeepen);
				}
				reqBuilder.setDeepenSince(ts);
			} else if (transferConfig.isAllowFilter()
					&& line.startsWith(OPTION_FILTER + ' ')) {
				if (filterReceived) {
					throw new PackProtocolException(
							JGitText.get().tooManyFilters);
				}
				filterReceived = true;
				reqBuilder.setFilterSpec(FilterSpec.fromFilterLine(
						line.substring(OPTION_FILTER.length() + 1)));
			} else {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().unexpectedPacketLine
			}
		}

		return reqBuilder.build();
	}

	LsRefsV2Request parseLsRefsRequest(PacketLineIn pckIn)
			throws PackProtocolException
		LsRefsV2Request.Builder builder = LsRefsV2Request.builder();
		List<String> prefixes = new ArrayList<>();

		String line = consumeCapabilities(pckIn
				serverOption -> builder.addServerOption(serverOption)
				agent -> builder.setAgent(agent));

		if (line == PacketLineIn.END) {
			return builder.build();
		}

		if (line != PacketLineIn.DELIM) {
			throw new PackProtocolException(MessageFormat
					.format(JGitText.get().unexpectedPacketLine
		}

		while ((line = pckIn.readString()) != PacketLineIn.END) {
				builder.setPeel(true);
				builder.setSymrefs(true);
			} else {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().unexpectedPacketLine
			}
		}

		return builder.setRefPrefixes(prefixes).build();
	}

}
