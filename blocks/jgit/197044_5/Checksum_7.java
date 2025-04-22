
package org.eclipse.jgit.binarydiff;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BinaryDiffWriter {

    public static final int CHUNK_SIZE = Byte.MAX_VALUE;

    private final ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
    private final DataOutputStream output;

    public BinaryDiffWriter(DataOutputStream outputStream) {
        this.output = outputStream;
    }

    public BinaryDiffWriter(OutputStream outputStream) {
        this(new DataOutputStream(outputStream));
    }

    public void writeFileSize(long fileSize) throws IOException {
        encode(fileSize);
    }

    private void encode(long val) throws IOException {
        while ( val > 0x7f) {
            output.write((byte)(val & 0x7f | 0x80));
            val >>>= 7;
        }
        output.write((byte)val);
    }

    private void writeOpcodes(long offset
        int code = 0x80;
        int codeIdx = 0;

        List<Integer> opcodes = new ArrayList<>();
        opcodes.add(0);

        if ((offset & 0xff) > 0) {
            opcodes.add((int)(offset & 0xff));
            code |= 0x01;
        }

        if ((offset & 0xff00) > 0) {
            opcodes.add((int)(offset & 0xff00) >>> 8);
            code |= 0x02;
        }

        if ((offset & 0xff0000) > 0) {
            opcodes.add((int)(offset & 0xff0000) >>> 16);
            code |= 0x04;
        }

        if ((offset & 0xff000000) > 0) {
            opcodes.add((int)(offset & 0xff000000) >>> 24);
            code |= 0x08;
        }

        if ((length & 0xff) > 0) {
            opcodes.add((int)(length & 0xff));
            code |= 0x10;
        }

        if ((length & 0xff00) > 0) {
            opcodes.add((int)(length & 0xff00) >>> 8);
            code |= 0x20;
        }

        if ((length & 0xff0000) > 0) {
            opcodes.add((int)(length & 0xff0000) >>> 16);
            code |= 0x40;
        }

        opcodes.set(codeIdx

        for (Integer opcode : opcodes) {
            output.write(opcode);
        }
    }

    public void addCopy(long offset
        writeBuffer();
        writeOpcodes(offset
    }

    public void addData(byte dataByte) throws IOException {
        byteBuff.write(dataByte);
        if (byteBuff.size() >= CHUNK_SIZE) {
            writeBuffer();
        }
    }

    private void writeBuffer() throws IOException {
        if (byteBuff.size() > 0) {
            output.write(byteBuff.size());
        }
        byteBuff.writeTo(output);
        byteBuff.reset();
    }

    public void flush() throws IOException {
        writeBuffer();
    	output.flush(); 
    }

    public void close() throws IOException {
        this.flush();
        output.close();
    }

}

