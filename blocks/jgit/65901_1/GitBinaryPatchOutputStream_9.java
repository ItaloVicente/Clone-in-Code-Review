package org.eclipse.jgit.patch.binary;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GitBinaryPatchInputStream extends FilterInputStream
{
	private BufferedReader sourceReader;
	private InputStream resultBuffer;
	private int linenr = 0;

	public GitBinaryPatchInputStream(InputStream in)
	{
		super(in);
		sourceReader = new BufferedReader(new InputStreamReader(in
	}

	public int read() throws IOException
	{
		if (available() > 0)
		{
			return resultBuffer.read();
		}
		else
		{
			return -1;
		}
	}

	public int read(byte b[]
	{
		if (b == null)
		{
			throw new NullPointerException();
		}
		else if (off < 0 || len < 0 || len > b.length - off)
		{
			throw new IndexOutOfBoundsException();
		}
		else if (len == 0)
		{
			return 0;
		}

		int bytesRead = 0;
		int available;
		while (len > 0 && (available = available()) > 0)
		{
			int read = resultBuffer.read(b
			off += read;
			len -= read;
			bytesRead += read;
		}
		if (bytesRead == 0)
		{
			bytesRead = -1;
		}
		return bytesRead;
	}

	public long skip(long n) throws IOException
	{
	}

	public int available() throws IOException
	{
		if (resultBuffer == null || resultBuffer.available() == 0)
		{
			if (!readNextLine())
			{
				return 0;
			}
		}
		return resultBuffer.available();
	}

	public void close() throws IOException
	{
		sourceReader.close();
	}

	public synchronized void mark(int readlimit)
	{
	}

	public synchronized void reset() throws IOException
	{
	}

	public boolean markSupported()
	{
		return false;
	}

	private boolean readNextLine() throws IOException
	{

		String line = sourceReader.readLine();
		if (line == null)
		{
			return false;
		}

		linenr++;
		int byte_length
		int llen = line.length() + 1;
		if (llen == 1)
		{
			return false;
		}

		if ((llen < 7) || (llen - 2) % 5 != 0)
		{
			throw new IOException(String.format(
"corrupt binary patch at line %d: %s"
							Integer.valueOf(linenr - 1)
		}
		max_byte_length = (llen - 2) / 5 * 4;
		byte_length = line.charAt(0);
		if ('A' <= byte_length && byte_length <= 'Z')
		{
			byte_length = byte_length - 'A' + 1;
		}
		else if ('a' <= byte_length && byte_length <= 'z')
		{
			byte_length = byte_length - 'a' + 27;
		}
		else
		{
			throw new IOException(String.format(
"corrupt binary patch at line %d: %s"
							Integer.valueOf(linenr - 1)
		}

		if (max_byte_length < byte_length || byte_length <= max_byte_length - 4)
		{
			throw new IOException(String.format(
"corrupt binary patch at line %d: %s"
							Integer.valueOf(linenr - 1)
		}

		try
		{
			resultBuffer = new ByteArrayInputStream(GitBase85Codec.decodeBase85(line.substring(1)
		} catch (Exception e)
		{
			throw new IOException(
					String.format("corrupt binary patch at line %d: %s"
							Integer.valueOf(linenr - 1)
					e);
		}

		return resultBuffer.available() > 0;
	}

}
