package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.IntList;

final class PackReverseIndexWriterV1 extends PackReverseIndexWriter {
	private static final int OID_VERSION_SHA1 = 1;

	private static final int DEFAULT_OID_VERSION = OID_VERSION_SHA1;

	PackReverseIndexWriterV1(final OutputStream dst) {
		super(dst);
	}

	@Override
	protected void writeHeader() throws IOException {
		out.write(MAGIC);
		dataOutput.writeInt(VERSION_1);
		dataOutput.writeInt(DEFAULT_OID_VERSION);
	}

	@Override
	protected void writeBody(
			List<? extends PackedObjectInfo> objectsSortedByIndexPosition)
			throws IOException {
		IntList indexPositionsInOffsetOrder = new IntList(0
				objectsSortedByIndexPosition.size());
		Comparator<Integer> compareByOffset = Comparator
				.comparingLong(indexPosition -> objectsSortedByIndexPosition
						.get(indexPosition).getOffset());
		indexPositionsInOffsetOrder.sortUsingComparator(compareByOffset);

		for (int i = 0; i < indexPositionsInOffsetOrder.size(); i++) {
			int indexPosition = indexPositionsInOffsetOrder.get(i);
			dataOutput.writeInt(indexPosition);
		}
	}
}
