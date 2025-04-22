package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_DEEPEN_RELATIVE;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_FILTER;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_INCLUDE_TAG;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_NO_PROGRESS;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_OFS_DELTA;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_SIDE_BAND_64K;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_THIN_PACK;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_WANT_REF;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;

final class ProtocolV2Parser {

	final private TransferConfig transferConfig;

	ProtocolV2Parser(TransferConfig transferConfig) {
		this.transferConfig = transferConfig;
	}

	FetchV2Request parseFetchRequest(PacketLineIn pckIn
			throws PackProtocolException
		FetchV2Request.Builder reqBuilder = FetchV2Request.builder();

		reqBuilder.addOption(OPTION_SIDE_BAND_64K);

		String line;

		if ((line = pckIn.readString()) != PacketLineIn.DELIM) {
			throw new PackProtocolException(MessageFormat
					.format(JGitText.get().unexpectedPacketLine
		}

		boolean filterReceived = false;
		while ((line = pckIn.readString()) != PacketLineIn.END) {
				reqBuilder.addWantsIds(ObjectId.fromString(line.substring(5)));
			} else if (transferConfig.isAllowRefInWant()
				String refName = line.substring(OPTION_WANT_REF.length() + 1);
				Ref ref = refdb.exactRef(refName);
				if (ref == null) {
					throw new PackProtocolException(MessageFormat
							.format(JGitText.get().invalidRefName
				}
				ObjectId oid = ref.getObjectId();
				if (oid == null) {
					throw new PackProtocolException(MessageFormat
							.format(JGitText.get().invalidRefName
				}
				reqBuilder.addWantedRef(refName
				reqBuilder.addWantsIds(oid);
				reqBuilder.addPeerHas(ObjectId.fromString(line.substring(5)));
				reqBuilder.setDoneReceived();
			} else if (line.equals(OPTION_THIN_PACK)) {
				reqBuilder.addOption(OPTION_THIN_PACK);
			} else if (line.equals(OPTION_NO_PROGRESS)) {
				reqBuilder.addOption(OPTION_NO_PROGRESS);
			} else if (line.equals(OPTION_INCLUDE_TAG)) {
				reqBuilder.addOption(OPTION_INCLUDE_TAG);
			} else if (line.equals(OPTION_OFS_DELTA)) {
				reqBuilder.addOption(OPTION_OFS_DELTA);
				reqBuilder.addClientShallowCommit(
						ObjectId.fromString(line.substring(8)));
				int parsedDepth = Integer.parseInt(line.substring(7));
				if (parsedDepth <= 0) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidDepth
									Integer.valueOf(parsedDepth)));
				}
				if (reqBuilder.getShallowSince() != 0) {
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
				reqBuilder.addOption(OPTION_DEEPEN_RELATIVE);
				int parsedShallowSince = Integer.parseInt(line.substring(13));
				if (parsedShallowSince <= 0) {
					throw new PackProtocolException(MessageFormat
							.format(JGitText.get().invalidTimestamp
				}
				if (reqBuilder.getDepth() != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenSinceWithDeepen);
				}
				reqBuilder.setShallowSince(parsedShallowSince);
			} else if (transferConfig.isAllowFilter()
					&& line.startsWith(OPTION_FILTER + ' ')) {
				if (filterReceived) {
					throw new PackProtocolException(
							JGitText.get().tooManyFilters);
				}
				filterReceived = true;
				reqBuilder.setFilterBlobLimit(filterLine(
						line.substring(OPTION_FILTER.length() + 1)));
			} else {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().unexpectedPacketLine
			}
		}

		return reqBuilder.build();
	}

	long filterLine(String arg) throws PackProtocolException {
		long blobLimit = -1;

			blobLimit = 0;
			try {
				blobLimit = Long
			} catch (NumberFormatException e) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidFilter
			}
		}
		if (blobLimit < 0) {
			throw new PackProtocolException(
					MessageFormat.format(JGitText.get().invalidFilter
		}

		return blobLimit;
	}

}
