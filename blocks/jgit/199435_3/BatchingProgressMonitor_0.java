package org.eclipse.jgit.lib;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextProgressMonitorTest {

	TextProgressMonitor m;

	ByteArrayOutputStream buf;

	@Before
	public void setup() {
		buf = new ByteArrayOutputStream();
		m = new TextProgressMonitor(
				new OutputStreamWriter(buf
	}

	@Test
	public void testSimple() throws Exception {
		m.beginTask("task"
		for (int i = 0; i < 10; i++) {
			m.update(1);
		}
		m.endTask();
		Assert.assertArrayEquals(
				new String[] { ""
						"task:                    20% ( 2/10)"
						"task:                    30% ( 3/10)"
						"task:                    40% ( 4/10)"
						"task:                    50% ( 5/10)"
						"task:                    60% ( 6/10)"
						"task:                    70% ( 7/10)"
						"task:                    80% ( 8/10)"
						"task:                    90% ( 9/10)"
						"task:                   100% (10/10)"
						"task:                   100% (10/10)\n" }
				bufLines());
	}

	@Test
	public void testLargeNumbers() throws Exception {
		m.beginTask("task"
		for (int i = 0; i < 10; i++) {
			m.update(100_000_000);
		}
		m.endTask();
		Assert.assertArrayEquals(
				new String[] { ""
						"task:                    10% ( 100000000/1000000000)"
						"task:                    20% ( 200000000/1000000000)"
						"task:                    30% ( 300000000/1000000000)"
						"task:                    40% ( 400000000/1000000000)"
						"task:                    50% ( 500000000/1000000000)"
						"task:                    60% ( 600000000/1000000000)"
						"task:                    70% ( 700000000/1000000000)"
						"task:                    80% ( 800000000/1000000000)"
						"task:                    90% ( 900000000/1000000000)"
						"task:                   100% (1000000000/1000000000)"
						"task:                   100% (1000000000/1000000000)\n" }
				bufLines());
	}

	String[] bufLines() throws UnsupportedEncodingException {
		String s = new String(buf.toString(StandardCharsets.UTF_8.name()));
		return s.split("\r");
	}
}
