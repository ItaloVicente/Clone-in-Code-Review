
package org.eclipse.jgit.util;

import java.nio.*;
import java.nio.charset.*;

public class InvalidCharset extends Charset {

	public static final InvalidCharset INSTANCE = new InvalidCharset();

	private InvalidCharset() {
		super("Invalid"
	}

	@Override
	public boolean contains(Charset cs) {
		return false;
	}

	@Override
	public CharsetDecoder newDecoder() {
		return new CharsetDecoder(this
			@Override
			protected CoderResult decodeLoop(ByteBuffer in
				if (in.remaining() > 0) {
					return CoderResult.unmappableForLength(1);
				}

				return CoderResult.UNDERFLOW;
			}
		};
	}

	@Override
	public CharsetEncoder newEncoder() {
		return new CharsetEncoder(this
			@Override
			protected CoderResult encodeLoop(CharBuffer in
				if (in.remaining() > 0) {
					return CoderResult.unmappableForLength(1);
				}

				return CoderResult.UNDERFLOW;
			}
		};
	}
}
