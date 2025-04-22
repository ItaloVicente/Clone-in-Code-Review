package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.ObjectIdMatcher.hasOnlyObjectIds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.lib.Config;
import org.junit.Test;

public class ProtocolV0ParserTest {
	private static PacketLineIn formatAsPacketLine(String... inputLines)
			throws IOException {
		ByteArrayOutputStream send = new ByteArrayOutputStream();
		PacketLineOut pckOut = new PacketLineOut(send);
		for (String line : inputLines) {
			if (line == PacketLineIn.END) {
				pckOut.end();
			} else if (line == PacketLineIn.DELIM) {
				pckOut.writeDelim();
			} else {
				pckOut.writeString(line);
			}
		}

		return new PacketLineIn(new ByteArrayInputStream(send.toByteArray()));
	}

	private static TransferConfig defaultConfig() {
		Config rc = new Config();
		rc.setBoolean("uploadpack"
		return new TransferConfig(rc);
	}

	@Test
	public void testRecvWantsWithCapabilities()
			throws PackProtocolException
		PacketLineIn pckIn = formatAsPacketLine(
				String.join(" "
						"4624442d68ee402a94364191085b77137618633e"
						"no-progress"
				"want f900c8326a43303685c46b279b9f70411bff1a4b\n"
				PacketLineIn.END);
		ProtocolV0Parser parser = new ProtocolV0Parser(defaultConfig());
		FetchV0Request request = parser.recvWants(pckIn);
		assertTrue(request.getClientCapabilities()
				.contains(GitProtocolConstants.OPTION_THIN_PACK));
		assertTrue(request.getClientCapabilities()
				.contains(GitProtocolConstants.OPTION_NO_PROGRESS));
		assertTrue(request.getClientCapabilities()
				.contains(GitProtocolConstants.OPTION_INCLUDE_TAG));
		assertTrue(request.getClientCapabilities()
				.contains(GitProtocolConstants.CAPABILITY_OFS_DELTA));
		assertThat(request.getWantIds()
				hasOnlyObjectIds("4624442d68ee402a94364191085b77137618633e"
						"f900c8326a43303685c46b279b9f70411bff1a4b"));
	}

	@Test
	public void testRecvWantsWithAgent()
			throws PackProtocolException
		PacketLineIn pckIn = formatAsPacketLine(
				String.join(" "
						"4624442d68ee402a94364191085b77137618633e"
						"agent=JGit.test/0.0.1"
				"want f900c8326a43303685c46b279b9f70411bff1a4b\n"
				PacketLineIn.END);
		ProtocolV0Parser parser = new ProtocolV0Parser(defaultConfig());
		FetchV0Request request = parser.recvWants(pckIn);
		assertTrue(request.getClientCapabilities()
				.contains(GitProtocolConstants.OPTION_THIN_PACK));
		assertEquals(1
		assertEquals("JGit.test/0.0.1"
		assertThat(request.getWantIds()
				hasOnlyObjectIds("4624442d68ee402a94364191085b77137618633e"
						"f900c8326a43303685c46b279b9f70411bff1a4b"));
	}

	@Test
	public void testRecvWantsWithoutCapabilities()
			throws PackProtocolException
		PacketLineIn pckIn = formatAsPacketLine(
				"want 4624442d68ee402a94364191085b77137618633e\n"
				"want f900c8326a43303685c46b279b9f70411bff1a4b\n"
				PacketLineIn.END);
		ProtocolV0Parser parser = new ProtocolV0Parser(defaultConfig());
		FetchV0Request request = parser.recvWants(pckIn);
		assertTrue(request.getClientCapabilities().isEmpty());
		assertThat(request.getWantIds()
				hasOnlyObjectIds("4624442d68ee402a94364191085b77137618633e"
						"f900c8326a43303685c46b279b9f70411bff1a4b"));
	}

	@Test
	public void testRecvWantsDeepen()
			throws PackProtocolException
		PacketLineIn pckIn = formatAsPacketLine(
				"want 4624442d68ee402a94364191085b77137618633e\n"
				"want f900c8326a43303685c46b279b9f70411bff1a4b\n"
				PacketLineIn.END);
		ProtocolV0Parser parser = new ProtocolV0Parser(defaultConfig());
		FetchV0Request request = parser.recvWants(pckIn);
		assertTrue(request.getClientCapabilities().isEmpty());
		assertEquals(3
		assertThat(request.getWantIds()
				hasOnlyObjectIds("4624442d68ee402a94364191085b77137618633e"
						"f900c8326a43303685c46b279b9f70411bff1a4b"));
	}

	@Test
	public void testRecvWantsShallow()
			throws PackProtocolException
		PacketLineIn pckIn = formatAsPacketLine(
				"want 4624442d68ee402a94364191085b77137618633e\n"
				"want f900c8326a43303685c46b279b9f70411bff1a4b\n"
				"shallow 4b643d0ef739a1b494e7d6926d8d8ed80d35edf4\n"
				PacketLineIn.END);
		ProtocolV0Parser parser = new ProtocolV0Parser(defaultConfig());
		FetchV0Request request = parser.recvWants(pckIn);
		assertTrue(request.getClientCapabilities().isEmpty());
		assertThat(request.getWantIds()
				hasOnlyObjectIds("4624442d68ee402a94364191085b77137618633e"
						"f900c8326a43303685c46b279b9f70411bff1a4b"));
		assertThat(request.getClientShallowCommits()
				hasOnlyObjectIds("4b643d0ef739a1b494e7d6926d8d8ed80d35edf4"));
	}

	@Test
	public void testRecvWantsFilter()
			throws PackProtocolException
		PacketLineIn pckIn = formatAsPacketLine(
				"want 4624442d68ee402a94364191085b77137618633e\n"
				"want f900c8326a43303685c46b279b9f70411bff1a4b\n"
				"filter blob:limit=13000\n"
				PacketLineIn.END);
		ProtocolV0Parser parser = new ProtocolV0Parser(defaultConfig());
		FetchV0Request request = parser.recvWants(pckIn);
		assertTrue(request.getClientCapabilities().isEmpty());
		assertThat(request.getWantIds()
				hasOnlyObjectIds("4624442d68ee402a94364191085b77137618633e"
						"f900c8326a43303685c46b279b9f70411bff1a4b"));
		assertEquals(13000
	}

}
