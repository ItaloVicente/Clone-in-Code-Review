package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.jgit.lib.Config;
import org.junit.Test;

public class TransferConfigTest {

	@Test
	public void testParseProtocolV0() {
		Config rc = new Config();
		rc.setInt("protocol"
		TransferConfig tc = new TransferConfig(rc);
		assertEquals(TransferConfig.ProtocolVersion.V0
	}

	@Test
	public void testParseProtocolV1() {
		Config rc = new Config();
		rc.setInt("protocol"
		TransferConfig tc = new TransferConfig(rc);
		assertEquals(TransferConfig.ProtocolVersion.V0
	}

	@Test
	public void testParseProtocolV2() {
		Config rc = new Config();
		rc.setInt("protocol"
		TransferConfig tc = new TransferConfig(rc);
		assertEquals(TransferConfig.ProtocolVersion.V2
	}

	@Test
	public void testParseProtocolNotSet() {
		Config rc = new Config();
		TransferConfig tc = new TransferConfig(rc);
		assertNull(tc.protocolVersion);
	}

	@Test
	public void testParseProtocolUnknown() {
		Config rc = new Config();
		rc.setInt("protocol"
		TransferConfig tc = new TransferConfig(rc);
		assertNull(tc.protocolVersion);
	}

	@Test
	public void testParseProtocolInvalid() {
		Config rc = new Config();
		rc.setString("protocol"
		TransferConfig tc = new TransferConfig(rc);
		assertNull(tc.protocolVersion);
	}
}
