	private void writeBloomIndexes(CommitGraphOutputStream out)
			throws IOException {
		byte[] tmp = new byte[4];
		long curPos = 0;

		for (ObjectToCommitData oc : commitDataList) {
			ChangedPathFilter filter = oc.getBloomFilter();
			long len = filter != null ? filter.getSize() : 0;
			curPos += len;
			NB.encodeInt32(tmp
			out.write(tmp);
			out.updateMonitor();
		}
	}

	private void writeBloomData(CommitGraphOutputStream out)
			throws IOException {
		byte[] tmp = new byte[4];
		NB.encodeInt32(tmp
		out.write(tmp);
		NB.encodeInt32(tmp
		out.write(tmp);
		NB.encodeInt32(tmp
		out.write(tmp);

		for (ObjectToCommitData oc : commitDataList) {
			ChangedPathFilter filter = oc.getBloomFilter();
			if (filter != null) {
				byte[] data = filter.getData();
				out.write(data);
			}
			out.updateMonitor();
		}
	}

