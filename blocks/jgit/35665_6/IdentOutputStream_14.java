package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.diff.RawText;

public class IdentInputStream extends InputStream {

	static final int DEFAULT_MEMORY_BUFFER_SIZE = 4096;

	static final int DEFAULT_BUFFER_SIZE = 8096;

	int bufferSize;

	int memoryBufferSize;

	private static final byte[] START_IDENT_PATTERN = new byte[] { '$'
			'd'

	private static final byte END_IDENT_PATTERN = '$';

	private final byte[] single = new byte[1];

	private final byte[] auxBuffer;

	private int auxBufferLengh;

	private int auxBufferIndex;

	private final InputStream wrappedInputStream;

	private final byte[] memory;

	private int matchingByteFound = 0;

	private int memoryIndex = 0;

	private int memoryBufferLengh = 0;

	private boolean isBinary;

	private boolean detectBinary;

	private boolean abortIfBinary;

	public static class IsBinaryException extends IOException {
		private static final long serialVersionUID = 1L;

		IsBinaryException() {
			super();
		}
	}

	public IdentInputStream(InputStream in
		this(in
	}

	IdentInputStream(InputStream in
			boolean abortIfBinary
		this.wrappedInputStream = in;
		this.detectBinary = detectBinary;
		this.abortIfBinary = abortIfBinary;
		this.bufferSize = bufferSize;
		this.memoryBufferSize = memoryBuffer;
		this.memory = new byte[memoryBufferSize];
		this.auxBuffer = new byte[bufferSize];
	}

	public IdentInputStream(InputStream in
			boolean abortIfBinary) {
		this(in
				DEFAULT_MEMORY_BUFFER_SIZE);
	}

	@Override
	public int read() throws IOException {
		final int read = read(single
		return read == 1 ? single[0] & 0xff : -1;
	}

	@Override
	public int read(byte[] bs
		if (len == 0)
			return 0;
		if (auxBufferLengh == -1 && !isMemoryBufferNotEmpty())
			return -1;

		int currentReadBufferIndex = off;
		int lastByteToReadIndex = off + len;

		boolean somethingWritten = false;

		while (currentReadBufferIndex < lastByteToReadIndex) {
			if (!hasNextByte()) {
				break;
			}

			byte currentAuxBufferCurentByte = getNextByte();

			if (matchPattern(currentAuxBufferCurentByte)) {
				currentAuxBufferCurentByte = END_IDENT_PATTERN;
			}

			bs[currentReadBufferIndex++] = currentAuxBufferCurentByte;
			somethingWritten = true;
		}

		return somethingWritten ? currentReadBufferIndex - off : -1;
	}

	private boolean matchPattern(byte currentAuxBufferCurentByte)
			throws IOException {
		boolean patternMatched = false;
		if (currentAuxBufferCurentByte == START_IDENT_PATTERN[matchingByteFound]) {
			if (matchingByteFound == START_IDENT_PATTERN.length - 1) {
				matchingByteFound = 0;
				if (fillMemoryBuffer()) {

					patternMatched = true;
					memoryIndex = 0;
					memoryBufferLengh = 0;
				}

			} else {
				matchingByteFound++;
			}
		} else {
			matchingByteFound = 0;
		}
		return patternMatched;
	}

	private boolean hasNextByte() throws IOException {
		return isMemoryBufferNotEmpty() || auxBufferIndex < auxBufferLengh
				|| fillAuxBuffer();
	}

	private byte getNextByte() {
		final byte currentAuxBufferCurentByte;
		if (isMemoryBufferNotEmpty()) {
			currentAuxBufferCurentByte = memory[memoryIndex++];
		} else {
			currentAuxBufferCurentByte = auxBuffer[auxBufferIndex++];
		}
		return currentAuxBufferCurentByte;
	}

	private boolean isMemoryBufferNotEmpty() {
		return memoryIndex < memoryBufferLengh;
	}

	private boolean fillMemoryBuffer() throws IOException {
		int memoryBufferLenghAux = 0;
		for (int index = 0; index < memory.length; index++) {
			if (hasNextByte()) {
				memory[index] = getNextByte();
				memoryBufferLenghAux = index + 1;
				if (END_IDENT_PATTERN == memory[index]) {
					memoryIndex = 0;
					memoryBufferLengh = memoryBufferLenghAux;
					return true;
				}
			} else {
				break;
			}

		}
		memoryIndex = 0;
		memoryBufferLengh = memoryBufferLenghAux;
		return false;

	}

	public boolean isBinary() {
		return isBinary;
	}

	@Override
	public void close() throws IOException {
		wrappedInputStream.close();
	}

	private boolean fillAuxBuffer() throws IOException {
		auxBufferLengh = wrappedInputStream
				.read(auxBuffer
		if (auxBufferLengh < 1)
			return false;
		if (detectBinary) {
			isBinary = RawText.isBinary(auxBuffer
			detectBinary = false;
			if (isBinary && abortIfBinary)
				throw new IsBinaryException();
		}
		auxBufferIndex = 0;
		return true;
	}

}
