
package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;

public class SignedPushConfig {
	public static final SectionParser<SignedPushConfig> KEY =
			SignedPushConfig::new;

	private String certNonceSeed;
	private int certNonceSlopLimit;
	private NonceGenerator nonceGenerator;

	public SignedPushConfig() {
	}

	SignedPushConfig(Config cfg) {
		setCertNonceSeed(cfg.getString("receive"
		certNonceSlopLimit = cfg.getInt("receive"
	}

	public void setCertNonceSeed(String seed) {
		certNonceSeed = seed;
	}

	public String getCertNonceSeed() {
		return certNonceSeed;
	}

	public void setCertNonceSlopLimit(int limit) {
		certNonceSlopLimit = limit;
	}

	public int getCertNonceSlopLimit() {
		return certNonceSlopLimit;
	}

	public void setNonceGenerator(NonceGenerator generator) {
		nonceGenerator = generator;
	}

	public NonceGenerator getNonceGenerator() {
		if (nonceGenerator != null) {
			return nonceGenerator;
		} else if (certNonceSeed != null) {
			return new HMACSHA1NonceGenerator(certNonceSeed);
		}
		return null;
	}
}
