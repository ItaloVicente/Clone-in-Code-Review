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
	protected void writeBody(List<? extends PackedObjectInfo> objectsByIndexPos)
			throws IOException {
		IntList positionsByOffset = IntList.filledWithRange(0
				objectsByIndexPos.size());
		Comparator<Integer> compareByOffset = Comparator
				.comparingLong(indexPosition -> objectsByIndexPos
						.get(indexPosition).getOffset());
		positionsByOffset.sort(compareByOffset);

		for (int i = 0; i < positionsByOffset.size(); i++) {
			int indexPosition = positionsByOffset.get(i);
			dataOutput.writeInt(indexPosition);
		}
	}
}
