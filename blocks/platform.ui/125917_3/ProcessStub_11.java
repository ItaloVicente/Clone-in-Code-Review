package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessStub implements IProcessExecutor {

	List<Record> records = new ArrayList<>();

	@Override
	public void execute(String process, String... args) throws IOException {
		records.add(new Record(process, args));
	}

	class Record {
		String process;
		String[] args;

		public Record(String process, String[] args) {
			this.process = process;
			this.args = args;

		}
	}
}
