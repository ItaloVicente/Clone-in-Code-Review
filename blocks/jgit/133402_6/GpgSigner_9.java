package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.Serializable;

public class GpgSignature implements Serializable {

	private static final long serialVersionUID = 1L;

	private byte[] signature;

	public GpgSignature(byte[] signature) {
		this.signature = signature;
	}

	public String toExternalString() {
		return new String(signature
	}

	@Override
	@SuppressWarnings("nls")
	public String toString() {
		final StringBuilder r = new StringBuilder();

		r.append("GpgSignature[");
		r.append(this.signature != null ? "size " + signature.length : "null");
		r.append("]");

		return r.toString();
	}
}
