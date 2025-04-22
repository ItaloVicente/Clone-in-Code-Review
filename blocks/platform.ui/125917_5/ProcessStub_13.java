package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessStub implements IProcessExecutor {

	List<Record> records = new ArrayList<>();
	String result;

	@Override
	public String execute(String process, String... args) throws IOException {
		records.add(new Record(process, args));
		return result;
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
