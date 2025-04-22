package org.eclipse.jgit.patch.binary;

import java.io.IOException;

public class GitBase85Codec
{
	private static final byte[] ENCODE_TABLE;
	private static final byte[] DECODE_TABLE;

	static
	{
		ENCODE_TABLE = new byte[] { '0'
				'H'
				'c'
				'x'
				'`'

		DECODE_TABLE = new byte[256];
		for (int i = 0; i < DECODE_TABLE.length; ++i)
		{
			DECODE_TABLE[i] = -1;
		}

		for (int i = 0; i < ENCODE_TABLE.length; ++i)
		{
			DECODE_TABLE[(ENCODE_TABLE[i] & 0xFF)] = (byte) i;
		}
	}

	public static byte[] decodeBase85(String encoded
			throws IOException
	{
		byte[] result = new byte[len];
		int i = 0;
		int j = 0;
		while (len > 0)
		{
			long acc = 0;
			byte de;
			int cnt = 4;
			char ch;
			do
			{
				ch = encoded.charAt(i++);
				de = DECODE_TABLE[ch];
				if (de < 0)
				{
					throw new IOException(
							String.format("invalid base85 alphabet %c"
									Character.valueOf(ch)));
				}
				acc = acc * 85 + de;
			} while (--cnt > 0);
			ch = encoded.charAt(i++);
			de = DECODE_TABLE[ch];
			if (de < 0)
			{
				throw new IOException(String.format(
						"invalid base85 alphabet %c"
			}
			if (0xffffffffL / 85 < acc || 0xffffffffL - de < (acc *= 85))
			{
				throw new IOException(
						String.format("invalid base85 sequence %.5s"
								encoded.substring(i - 5
			}
			acc += de;

			cnt = (len < 4) ? len : 4;
			len -= cnt;
			int word = (int) (acc & 0xFFFFFFFF);
			do
			{
				word = (word << 8) | (word >>> 24);
				result[j++] = (byte) (word & 0xFF);
			} while (--cnt > 0);
		}

		return result;
	}

	public static byte[] encodeBase85(byte[] data)
	{
		byte[] result = new byte[(data.length / 4 + (data.length % 4 > 0 ? 1 : 0)) * 5];

		int i = 0
		while (i < data.length)
		{
			long acc = 0;
			int cnt;
			for (cnt = 24; cnt >= 0; cnt -= 8)
			{
				long ch = data[i++] & 0xFF;
				acc |= ch << cnt;
				if (i == data.length)
				{
					break;
				}
			}

			for (cnt = 4; cnt >= 0; cnt--)
			{
				int val = (int) (acc % 85);
				acc /= 85;
				result[j + cnt] = ENCODE_TABLE[val];
			}
			j += 5;
		}
		return result;
	}
}
