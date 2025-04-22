package org.eclipse.jgit.patch.binary;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class GitBinaryPatchOutputStream extends FilterOutputStream
{
	private static final int BYTES_PER_LINE = 52;
	private byte[] outputBuffer = new byte[BYTES_PER_LINE];
	private int buffered;

	public GitBinaryPatchOutputStream(OutputStream out)
	{
		super(out);
	}

	public void write(byte b[]
	{
		while (len > 0)
		{
			int length = Math.min(len
			System.arraycopy(b
			off += length;
			len -= length;
			buffered += length;

			if (buffered == BYTES_PER_LINE)
			{
				writeLine();
			}
		}
	}

	public void write(int b) throws IOException
	{
		buffered++;
		if (buffered == BYTES_PER_LINE)
		{
			writeLine();
		}
	}

	public void flush() throws IOException {
		writeLine();
		out.write('\n');
		out.flush();
	}

	private void writeLine() throws IOException
	{
		if (buffered > 0)
		{
			int lineLength;
			if (buffered <= 26)
			{
				lineLength = buffered + 'A' - 1;
			}
			else
			{
				lineLength = buffered - 26 + 'a' - 1;
			}
			byte[] buffer = buffered == BYTES_PER_LINE ? outputBuffer : Arrays.copyOf(outputBuffer
			byte[] encodedBytes = GitBase85Codec.encodeBase85(buffer);
			out.write(lineLength);
			out.write(encodedBytes);
			out.write('\n');
			buffered = 0;
		}
	}
}
