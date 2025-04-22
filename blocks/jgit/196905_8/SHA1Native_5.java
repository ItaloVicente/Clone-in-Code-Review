
package org.eclipse.jgit.util.sha1;

import static java.lang.Integer.lowestOneBit;
import static java.lang.Integer.numberOfTrailingZeros;
import static java.lang.Integer.rotateLeft;
import static java.lang.Integer.rotateRight;

import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.SystemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SHA1Java extends SHA1 {
	private static final Logger LOG = LoggerFactory.getLogger(SHA1Java.class);
	private static final boolean DETECT_COLLISIONS;

	static {
		SystemReader sr = SystemReader.getInstance();
		DETECT_COLLISIONS = v != null ? Boolean.parseBoolean(v) : true;
	}

	private final State h = new State();
	private final int[] w = new int[80];

	private final byte[] buffer = new byte[64];

	private long length;

	private boolean detectCollision = DETECT_COLLISIONS;
	private boolean foundCollision;

	private final int[] w2 = new int[80];
	private final State state58 = new State();
	private final State state65 = new State();
	private final State hIn = new State();
	private final State hTmp = new State();

	SHA1Java() {
		h.init();
	}

	@Override
	public SHA1 setDetectCollision(boolean detect) {
		detectCollision = detect;
		return this;
	}

	@Override
	public void update(byte b) {
		int bufferLen = (int) (length & 63);
		length++;
		buffer[bufferLen] = b;
		if (bufferLen == 63) {
			compress(buffer
		}
	}

	@Override
	public void update(byte[] in) {
		update(in
	}

	@Override
	public void update(byte[] in
		int bufferLen = (int) (length & 63);
		length += len;

		if (bufferLen > 0) {
			int n = Math.min(64 - bufferLen
			System.arraycopy(in
			p += n;
			len -= n;
			if (bufferLen + n < 64) {
				return;
			}
			compress(buffer
		}
		while (len >= 64) {
			compress(in
			p += 64;
			len -= 64;
		}
		if (len > 0) {
			System.arraycopy(in
		}
	}

	private void compress(byte[] block
		initBlock(block
		int ubcDvMask = detectCollision ? UbcCheck.check(w) : 0;
		compress();

		while (ubcDvMask != 0) {
			int b = numberOfTrailingZeros(lowestOneBit(ubcDvMask));
			UbcCheck.DvInfo dv = UbcCheck.DV[b];
			for (int i = 0; i < 80; i++) {
				w2[i] = w[i] ^ dv.dm[i];
			}
			recompress(dv.testt);
			if (eq(hTmp
				foundCollision = true;
				break;
			}
			ubcDvMask &= ~(1 << b);
		}
	}

	private void initBlock(byte[] block
		for (int t = 0; t < 16; t++) {
			w[t] = NB.decodeInt32(block
		}

		for (int t = 16; t < 80; t++) {
			int x = w[t - 3] ^ w[t - 8] ^ w[t - 14] ^ w[t - 16];
			w[t] = rotateLeft(x
		}
	}

	private void compress() {
		int a = h.a

		 e += s1(a
		 d += s1(e
		 c += s1(d
		 b += s1(c
		 a += s1(b
		 e += s1(a
		 d += s1(e
		 c += s1(d
		 b += s1(c
		 a += s1(b
		 e += s1(a
		 d += s1(e
		 c += s1(d
		 b += s1(c
		 a += s1(b
		 e += s1(a
		 d += s1(e
		 c += s1(d
		 b += s1(c
		 a += s1(b

		 e += s2(a
		 d += s2(e
		 c += s2(d
		 b += s2(c
		 a += s2(b
		 e += s2(a
		 d += s2(e
		 c += s2(d
		 b += s2(c
		 a += s2(b
		 e += s2(a
		 d += s2(e
		 c += s2(d
		 b += s2(c
		 a += s2(b
		 e += s2(a
		 d += s2(e
		 c += s2(d
		 b += s2(c
		 a += s2(b

		 e += s3(a
		 d += s3(e
		 c += s3(d
		 b += s3(c
		 a += s3(b
		 e += s3(a
		 d += s3(e
		 c += s3(d
		 b += s3(c
		 a += s3(b
		 e += s3(a
		 d += s3(e
		 c += s3(d
		 b += s3(c
		 a += s3(b
		 e += s3(a
		 d += s3(e
		 c += s3(d
		state58.save(a
		 b += s3(c
		 a += s3(b

		 e += s4(a
		 d += s4(e
		 c += s4(d
		 b += s4(c
		 a += s4(b
		state65.save(a
		 e += s4(a
		 d += s4(e
		 c += s4(d
		 b += s4(c
		 a += s4(b
		 e += s4(a
		 d += s4(e
		 c += s4(d
		 b += s4(c
		 a += s4(b
		 e += s4(a
		 d += s4(e
		 c += s4(d
		 b += s4(c
		 a += s4(b

		h.save(h.a + a
	}

	private void recompress(int t) {
		State s;
		switch (t) {
		case 58:
			s = state58;
			break;
		case 65:
			s = state65;
			break;
		default:
			throw new IllegalStateException();
		}
		int a = s.a

	  if (t == 65) {
		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b

		{ c = rotateRight( c
		{ d = rotateRight( d
	  }
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b
		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b
		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b
		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b

		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b
		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b
		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b
		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b

		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b
		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b
		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b
		{ c = rotateRight( c
		{ d = rotateRight( d
		{ e = rotateRight( e
		{ a = rotateRight( a
		{ b = rotateRight( b

		hIn.save(a
		a = s.a; b = s.b; c = s.c; d = s.d; e = s.e;

	  if (t == 58) {
		{ b += s3(c
		{ a += s3(b

		{ e += s4(a
		{ d += s4(e
		{ c += s4(d
		{ b += s4(c
		{ a += s4(b
	  }
		{ e += s4(a
		{ d += s4(e
		{ c += s4(d
		{ b += s4(c
		{ a += s4(b
		{ e += s4(a
		{ d += s4(e
		{ c += s4(d
		{ b += s4(c
		{ a += s4(b
		{ e += s4(a
		{ d += s4(e
		{ c += s4(d
		{ b += s4(c
		{ a += s4(b

		hTmp.save(hIn.a + a
	}

	private static int s1(int a
		return rotateLeft(a
				+ ((b & c) | ((~b) & d))
				+ 0x5A827999 + w_t;
	}

	private static int s2(int a
		return rotateLeft(a
				+ (b ^ c ^ d)
				+ 0x6ED9EBA1 + w_t;
	}

	private static int s3(int a
		return rotateLeft(a
				+ ((b & c) | (b & d) | (c & d))
				+ 0x8F1BBCDC + w_t;
	}

	private static int s4(int a
		return rotateLeft(a
				+ (b ^ c ^ d)
				+ 0xCA62C1D6 + w_t;
	}

	private static boolean eq(State q
		return q.a == r.a
				&& q.b == r.b
				&& q.c == r.c
				&& q.d == r.d
				&& q.e == r.e;
	}

	private void finish() {
		int bufferLen = (int) (length & 63);
		if (bufferLen > 55) {
			buffer[bufferLen++] = (byte) 0x80;
			Arrays.fill(buffer
			compress(buffer
			Arrays.fill(buffer
		} else {
			buffer[bufferLen++] = (byte) 0x80;
			Arrays.fill(buffer
		}

		NB.encodeInt32(buffer
		NB.encodeInt32(buffer
		compress(buffer

		if (foundCollision) {
			ObjectId id = h.toObjectId();
			LOG.warn(MessageFormat.format(JGitText.get().sha1CollisionDetected
					id.name()));
			throw new Sha1CollisionException(id);
		}
	}

	@Override
	public byte[] digest() throws Sha1CollisionException {
		finish();

		byte[] b = new byte[20];
		NB.encodeInt32(b
		NB.encodeInt32(b
		NB.encodeInt32(b
		NB.encodeInt32(b
		NB.encodeInt32(b
		return b;
	}

	@Override
	public ObjectId toObjectId() throws Sha1CollisionException {
		finish();
		return h.toObjectId();
	}

	@Override
	public void digest(MutableObjectId id) throws Sha1CollisionException {
		finish();
		id.set(h.a
	}

	@Override
	public boolean hasCollision() {
		return foundCollision;
	}

	@Override
	public SHA1 reset() {
		h.init();
		length = 0;
		foundCollision = false;
		return this;
	}

	private static final class State {
		int a;
		int b;
		int c;
		int d;
		int e;

		final void init() {
			save(0x67452301
		}

		final void save(int a1
			a = a1;
			b = b1;
			c = c1;
			d = d1;
			e = e1;
		}

		ObjectId toObjectId() {
			return new ObjectId(a
		}
	}
}
