		boolean includeTag = false;
		boolean filterReceived = false;
		while ((line = pckIn.readString()) != PacketLineIn.END) {
				reqBuilder.addWantsIds(ObjectId.fromString(line.substring(5)));
			} else if (transferConfig.isAllowRefInWant() &&
				String refName = line.substring(OPTION_WANT_REF.length() + 1);
				Ref ref = db.getRefDatabase().exactRef(refName);
				if (ref == null) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidRefName,
								refName));
				}
				ObjectId oid = ref.getObjectId();
				if (oid == null) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidRefName,
								refName));
				}
				reqBuilder.addWantedRef(refName, oid);
				reqBuilder.addWantsIds(oid);
				reqBuilder.addPeerHas(ObjectId.fromString(line.substring(5)));
				reqBuilder.setDoneReceived();
			} else if (line.equals(OPTION_THIN_PACK)) {
				reqBuilder.addOption(OPTION_THIN_PACK);
			} else if (line.equals(OPTION_NO_PROGRESS)) {
				reqBuilder.addOption(OPTION_NO_PROGRESS);
			} else if (line.equals(OPTION_INCLUDE_TAG)) {
				reqBuilder.addOption(OPTION_INCLUDE_TAG);
				includeTag = true;
			} else if (line.equals(OPTION_OFS_DELTA)) {
				reqBuilder.addOption(OPTION_OFS_DELTA);
				reqBuilder.addClientShallowCommit(
						ObjectId.fromString(line.substring(8)));
				int parsedDepth = Integer.parseInt(line.substring(7));
				if (parsedDepth <= 0) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidDepth,
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
				reqBuilder.addOption(OPTION_DEEPEN_RELATIVE);
				int ts = Integer.parseInt(line.substring(13));
				if (ts <= 0) {
					throw new PackProtocolException(
							MessageFormat.format(
									JGitText.get().invalidTimestamp, line));
				}
				if (reqBuilder.getDepth() != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenSinceWithDeepen);
				}
				reqBuilder.setDeepenSince(ts);
			} else if (transferConfig.isAllowFilter()
					&& line.startsWith(OPTION_FILTER + ' ')) {
				if (filterReceived) {
					throw new PackProtocolException(JGitText.get().tooManyFilters);
				}
				filterReceived = true;
				reqBuilder.setFilterBlobLimit(parseFilter(
						line.substring(OPTION_FILTER.length() + 1)));
			} else {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().unexpectedPacketLine, line));
			}
		}
