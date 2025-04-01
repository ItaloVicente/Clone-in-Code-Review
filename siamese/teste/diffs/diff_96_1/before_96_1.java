
 import java.util.Arrays;
 import java.util.List;
 
import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.io.InterruptTimer;
 import org.eclipse.jgit.util.io.TimeoutInputStream;
 
public void testTimeout_readBuffer_Success1() throws IOException {
 		final byte[] exp = new byte[] { 'a', 'b', 'c' };
 		final byte[] act = new byte[exp.length];
 		out.write(exp);
		IO.readFully(is, act, 0, act.length);
 		assertTrue(Arrays.equals(exp, act));
 	}
 
 public void testTimeout_readBuffer_Success2() throws IOException {
 		final byte[] exp = new byte[] { 'a', 'b', 'c' };
 		final byte[] act = new byte[exp.length];
 		out.write(exp);
		IO.readFully(is, act, 0, 1);
		IO.readFully(is, act, 1, 1);
		IO.readFully(is, act, 2, 1);
 		assertTrue(Arrays.equals(exp, act));
 	}
 
 	public void testTimeout_readBuffer_Timeout() throws IOException {
 		beginRead();
 		try {
			IO.readFully(is, new byte[512], 0, 512);
 			fail("incorrectly read bytes");
 		} catch (InterruptedIOException e) {
 			// expected
 import java.util.Arrays;
 import java.util.List;
import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.io.InterruptTimer;
 import org.eclipse.jgit.util.io.TimeoutOutputStream;
 
 private final class FullPipeInputStream extends PipedInputStream {
 		}
 
 		void want(int cnt) throws IOException {
			IO.skipFully(this, PIPE_SIZE - cnt);
 		}
 
 		void free(int cnt) throws IOException {
			IO.skipFully(this, cnt);
 		}
 	}
 }
 import org.eclipse.jgit.lib.ObjectId;
 import org.eclipse.jgit.lib.ObjectWriter;
 import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.MutableInteger;
 import org.eclipse.jgit.util.NB;
 import org.eclipse.jgit.util.TemporaryBuffer;
private void readFrom(final FileInputStream inStream) throws IOException,
 		// Read the index header and verify we understand it.
 		//
 		final byte[] hdr = new byte[20];
		IO.readFully(in, hdr, 0, 12);
 		md.update(hdr, 0, 12);
 		if (!is_DIRC(hdr))
 			throw new CorruptObjectException("Not a DIRC file.");
private void readFrom(final FileInputStream inStream) throws IOException,
 		//
 		for (;;) {
 			in.mark(21);
		IO.readFully(in, hdr, 0, 20);
 			if (in.read() < 0) {
 				// No extensions present; the file ended where we expected.
 				//
private void readFrom(final FileInputStream inStream) throws IOException,
 			case EXT_TREE: {
 				final byte[] raw = new byte[NB.decodeInt32(hdr, 4)];
 				md.update(hdr, 0, 8);
				IO.skipFully(in, 8);
				IO.readFully(in, raw, 0, raw.length);
 				md.update(raw, 0, raw.length);
 				tree = new DirCacheTree(raw, new MutableInteger(), null);
 				break;
 private void readFrom(final FileInputStream inStream) throws IOException,
 					// a performance optimization. Since we do not
 					// understand it, we can safely skip past it.
 					//
					IO.skipFully(in, NB.decodeUInt32(hdr, 4));
 				} else {
 					// The extension is not an optimization and is
 					// _required_ to understand this index format.
 import org.eclipse.jgit.lib.Constants;
 import org.eclipse.jgit.lib.FileMode;
 import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.NB;
 
 /**
@@ -125,20 +126,20 @@ public class DirCacheEntry {
 		info = sharedInfo;
 		infoOffset = infoAt;
 
-		NB.readFully(in, info, infoOffset, INFO_LEN);
+		IO.readFully(in, info, infoOffset, INFO_LEN);
 		md.update(info, infoOffset, INFO_LEN);
 
 		int pathLen = NB.decodeUInt16(info, infoOffset + P_FLAGS) & NAME_MASK;
 		int skipped = 0;
 		if (pathLen < NAME_MASK) {
 			path = new byte[pathLen];
-			NB.readFully(in, path, 0, pathLen);
+			IO.readFully(in, path, 0, pathLen);
 			md.update(path, 0, pathLen);
 		} else {
 			final ByteArrayOutputStream tmp = new ByteArrayOutputStream();
 			{
 				final byte[] buf = new byte[NAME_MASK];
-				NB.readFully(in, buf, 0, NAME_MASK);
+				IO.readFully(in, buf, 0, NAME_MASK);
 				tmp.write(buf);
 			}
 			for (;;) {
@@ -163,7 +164,7 @@ public class DirCacheEntry {
 		final int expLen = (actLen + 8) & ~7;
 		final int padLen = expLen - actLen - skipped;
 		if (padLen > 0) {
-			NB.skipFully(in, padLen);
+			IO.skipFully(in, padLen);
 			md.update(nullpad, 0, padLen);
 		}
 	}
diff --git a/org.eclipse.jgit/src/org/eclipse/jgit/lib/FileBasedConfig.java b/org.eclipse.jgit/src/org/eclipse/jgit/lib/FileBasedConfig.java
index a1843d1189..0e5886ea11 100644
--- a/org.eclipse.jgit/src/org/eclipse/jgit/lib/FileBasedConfig.java
+++ b/org.eclipse.jgit/src/org/eclipse/jgit/lib/FileBasedConfig.java
@@ -3,7 +3,7 @@
  * Copyright (C) 2007, Dave Watson <dwatson@mimvista.com>
  * Copyright (C) 2009, Google Inc.
  * Copyright (C) 2009, JetBrains s.r.o.
- * Copyright (C) 2008, Robin Rosenberg <robin.rosenberg@dewire.com>
+ * Copyright (C) 2008-2009, Robin Rosenberg <robin.rosenberg@dewire.com>
  * Copyright (C) 2008, Shawn O. Pearce <spearce@spearce.org>
  * Copyright (C) 2008, Thad Hughes <thadh@thad.corp.google.com>
  * and other copyright owners as documented in the project's IP log.
@@ -54,7 +54,7 @@
 import java.io.IOException;
 
 import org.eclipse.jgit.errors.ConfigInvalidException;
-import org.eclipse.jgit.util.NB;
+import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.RawParseUtils;
 
 /**
@@ -104,7 +104,7 @@ public final File getFile() {
 	 */
 	public void load() throws IOException, ConfigInvalidException {
 		try {
			fromText(RawParseUtils.decode(IO.readFully(getFile())));
 		} catch (FileNotFoundException noFile) {
 			clear();
 		} catch (IOException e) {
 import org.eclipse.jgit.errors.CorruptObjectException;
 import org.eclipse.jgit.errors.PackInvalidException;
 import org.eclipse.jgit.errors.PackMismatchException;
import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.NB;
 import org.eclipse.jgit.util.RawParseUtils;
  ByteArrayWindow read(final long pos, int size) throws IOException {
 		if (length < pos + size)
 			size = (int) (length - pos);
 		final byte[] buf = new byte[size];
		IO.readFully(fd.getChannel(), pos, buf, 0, size);
 		return new ByteArrayWindow(this, pos, buf);
 	}
 
private void onOpenPack() throws IOException {
 		final PackIndex idx = idx();
 		final byte[] buf = new byte[20];
 
		IO.readFully(fd.getChannel(), 0, buf, 0, 12);
 		if (RawParseUtils.match(buf, 0, Constants.PACK_SIGNATURE) != 4)
 			throw new IOException("Not a PACK file.");
 		final long vers = NB.decodeUInt32(buf, 4);
private void onOpenPack() throws IOException {
 					+ " index " + idx.getObjectCount()
 					+ ": " + getPackFile());
 
		IO.readFully(fd.getChannel(), length - 20, buf, 0, 20);
 		if (!Arrays.equals(buf, packChecksum))
 			throw new PackMismatchException("Pack checksum mismatch:"
 					+ " pack " + ObjectId.fromRaw(buf).name()
 import java.util.Iterator;
 
 import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.NB;
 
 /**
@@ -84,7 +85,7 @@ public static PackIndex open(final File idxFile) throws IOException {
 		final FileInputStream fd = new FileInputStream(idxFile);
 		try {
 			final byte[] hdr = new byte[8];
-			NB.readFully(fd, hdr, 0, hdr.length);
+			IO.readFully(fd, hdr, 0, hdr.length);
 			if (isTOC(hdr)) {
 				final int v = NB.decodeInt32(hdr, 4);
 				switch (v) {
diff --git a/org.eclipse.jgit/src/org/eclipse/jgit/lib/PackIndexV1.java b/org.eclipse.jgit/src/org/eclipse/jgit/lib/PackIndexV1.java
index a7bf99e2fe..bb7cd8b53c 100644
--- a/org.eclipse.jgit/src/org/eclipse/jgit/lib/PackIndexV1.java
+++ b/org.eclipse.jgit/src/org/eclipse/jgit/lib/PackIndexV1.java
@@ -1,7 +1,7 @@
 /*
  * Copyright (C) 2008-2009, Google Inc.
  * Copyright (C) 2008, Marek Zawirski <marek.zawirski@gmail.com>
- * Copyright (C) 2007, Robin Rosenberg <robin.rosenberg@dewire.com>
+ * Copyright (C) 2007-2009, Robin Rosenberg <robin.rosenberg@dewire.com>
  * Copyright (C) 2006-2008, Shawn O. Pearce <spearce@spearce.org>
  * and other copyright owners as documented in the project's IP log.
  *
@@ -53,6 +53,7 @@
 import java.util.NoSuchElementException;
 
 import org.eclipse.jgit.errors.CorruptObjectException;
+import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.NB;
 
 class PackIndexV1 extends PackIndex {
@@ -68,7 +69,7 @@ class PackIndexV1 extends PackIndex {
 			throws CorruptObjectException, IOException {
 		final byte[] fanoutTable = new byte[IDX_HDR_LEN];
 		System.arraycopy(hdr, 0, fanoutTable, 0, hdr.length);
-		NB.readFully(fd, fanoutTable, hdr.length, IDX_HDR_LEN - hdr.length);
+		IO.readFully(fd, fanoutTable, hdr.length, IDX_HDR_LEN - hdr.length);
 
 		idxHeader = new long[256]; // really unsigned 32-bit...
 		for (int k = 0; k < idxHeader.length; k++)
@@ -83,13 +84,13 @@ class PackIndexV1 extends PackIndex {
 			}
 			if (n > 0) {
 				idxdata[k] = new byte[n * (Constants.OBJECT_ID_LENGTH + 4)];
-				NB.readFully(fd, idxdata[k], 0, idxdata[k].length);
+				IO.readFully(fd, idxdata[k], 0, idxdata[k].length);
 			}
 		}
 		objectCnt = idxHeader[255];
 
 		packChecksum = new byte[20];
-		NB.readFully(fd, packChecksum, 0, packChecksum.length);
+		IO.readFully(fd, packChecksum, 0, packChecksum.length);
 	}
 
 	long getObjectCount() {
diff --git a/org.eclipse.jgit/src/org/eclipse/jgit/lib/PackIndexV2.java b/org.eclipse.jgit/src/org/eclipse/jgit/lib/PackIndexV2.java
index c37ce646de..eb644c51c4 100644
--- a/org.eclipse.jgit/src/org/eclipse/jgit/lib/PackIndexV2.java
+++ b/org.eclipse.jgit/src/org/eclipse/jgit/lib/PackIndexV2.java
@@ -50,6 +50,7 @@
 import java.util.NoSuchElementException;
 
 import org.eclipse.jgit.errors.MissingObjectException;
+import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.NB;
 
 /** Support for the pack index v2 format. */
 class PackIndexV2 extends PackIndex {
 
 	PackIndexV2(final InputStream fd) throws IOException {
 		final byte[] fanoutRaw = new byte[4 * FANOUT];

		IO.readFully(fd, fanoutRaw, 0, fanoutRaw.length);
 		fanoutTable = new long[FANOUT];
 		for (int k = 0; k < FANOUT; k++)
 			fanoutTable[k] = NB.decodeUInt32(fanoutRaw, k * 4);
 class PackIndexV2 extends PackIndex {
 			final int intNameLen = (int) nameLen;
 			final byte[] raw = new byte[intNameLen];
 			final int[] bin = new int[intNameLen >>> 2];
			IO.readFully(fd, raw, 0, raw.length);
 			for (int i = 0; i < bin.length; i++)
 				bin[i] = NB.decodeInt32(raw, i << 2);
 
class PackIndexV2 extends PackIndex {
 
 		// CRC32 table.
 		for (int k = 0; k < FANOUT; k++)
			IO.readFully(fd, crc32[k], 0, crc32[k].length);
 
 		// 32 bit offset table. Any entries with the most significant bit
 		// set require a 64 bit offset entry in another table.
 class PackIndexV2 extends PackIndex {
 		int o64cnt = 0;
 		for (int k = 0; k < FANOUT; k++) {
 			final byte[] ofs = offset32[k];
			IO.readFully(fd, ofs, 0, ofs.length);
 			for (int p = 0; p < ofs.length; p += 4)
 				if (ofs[p] < 0)
 					o64cnt++;
class PackIndexV2 extends PackIndex {
 		//
 		if (o64cnt > 0) {
 			offset64 = new byte[o64cnt * 8];
			IO.readFully(fd, offset64, 0, offset64.length);
 		} else {
 			offset64 = NO_BYTES;
 		}
 
 		packChecksum = new byte[20];
		IO.readFully(fd, packChecksum, 0, packChecksum.length);
 	}
 
 	@Override
 import org.eclipse.jgit.errors.ObjectWritingException;
 import org.eclipse.jgit.lib.Ref.Storage;
 import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.RawParseUtils;
 
 class RefDatabase {
@@ -502,7 +502,7 @@ protected void writeFile(String name, byte[] content) throws IOException {
 
 	private static String readLine(final File file)
 			throws FileNotFoundException, IOException {
		final byte[] buf = IO.readFully(file, 4096);
 		int n = buf.length;
 
 		// remove trailing whitespaces
 import java.util.Collections;
 import java.util.List;
import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.RawParseUtils;
 
 /**
@@ -166,7 +166,7 @@ public List<Entry> getReverseEntries() throws IOException {
 	public List<Entry> getReverseEntries(int max) throws IOException {
 		final byte[] log;
 		try {
-			log = NB.readFully(logName);
+			log = IO.readFully(logName);
 		} catch (FileNotFoundException e) {
 			return Collections.emptyList();
 		}
diff --git a/org.eclipse.jgit/src/org/eclipse/jgit/lib/RepositoryCache.java b/org.eclipse.jgit/src/org/eclipse/jgit/lib/RepositoryCache.java
index e8630a3c6d..f0fc544de9 100644
--- a/org.eclipse.jgit/src/org/eclipse/jgit/lib/RepositoryCache.java
+++ b/org.eclipse.jgit/src/org/eclipse/jgit/lib/RepositoryCache.java
@@ -53,7 +53,7 @@
 
 import org.eclipse.jgit.errors.RepositoryNotFoundException;
 import org.eclipse.jgit.util.FS;
-import org.eclipse.jgit.util.NB;
+import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.RawParseUtils;
 
 /** Cache of active {@link Repository} instances. */
 private static boolean isValidHead(final File head) {
 
 		private static String readFirstLine(final File head) {
 			try {
				final byte[] buf = IO.readFully(head, 4096);
 				int n = buf.length;
 				if (n == 0)
 					return null;
 import java.util.zip.Inflater;
 
 import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.MutableInteger;
 import org.eclipse.jgit.util.RawParseUtils;
 
 /**
@@ -79,7 +79,7 @@ public class UnpackedObjectLoader extends ObjectLoader {
 	 */
 	public UnpackedObjectLoader(final File path, final AnyObjectId id)
 			throws IOException {
		this(IO.readFully(path), id);
 	}
 
 	/**
diff --git a/org.eclipse.jgit/src/org/eclipse/jgit/transport/BundleFetchConnection.java b/org.eclipse.jgit/src/org/eclipse/jgit/transport/BundleFetchConnection.java
index a8f0468cdd..6819635465 100644
--- a/org.eclipse.jgit/src/org/eclipse/jgit/transport/BundleFetchConnection.java
+++ b/org.eclipse.jgit/src/org/eclipse/jgit/transport/BundleFetchConnection.java
@@ -1,8 +1,7 @@
 /*
  * Copyright (C) 2009, Constantine Plotnikov <constantine.plotnikov@gmail.com>
  * Copyright (C) 2008-2009, Google Inc.
- * Copyright (C) 2009, Matthias Sohn <matthias.sohn@sap.com>
- * Copyright (C) 2008, Robin Rosenberg <robin.rosenberg@dewire.com>
+ * Copyright (C) 2008-2009, Robin Rosenberg <robin.rosenberg@dewire.com>
  * Copyright (C) 2009, Sasa Zivkov <sasa.zivkov@sap.com>
  * Copyright (C) 2008, Shawn O. Pearce <spearce@spearce.org>
  * and other copyright owners as documented in the project's IP log.
@@ -73,7 +72,7 @@
 import org.eclipse.jgit.revwalk.RevFlag;
 import org.eclipse.jgit.revwalk.RevObject;
 import org.eclipse.jgit.revwalk.RevWalk;
-import org.eclipse.jgit.util.NB;
+import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.RawParseUtils;
 
 /**
@@ -161,9 +160,9 @@ private String readLine(final byte[] hdrbuf) throws IOException {
 		while (lf < cnt && hdrbuf[lf] != '\n')
 			lf++;
 		bin.reset();
-		NB.skipFully(bin, lf);
+		IO.skipFully(bin, lf);
 		if (lf < cnt && hdrbuf[lf] == '\n')
-			NB.skipFully(bin, 1);
+			IO.skipFully(bin, 1);
 		return RawParseUtils.decode(Constants.CHARSET, hdrbuf, 0, lf);
 	}
 
diff --git a/org.eclipse.jgit/src/org/eclipse/jgit/transport/PacketLineIn.java b/org.eclipse.jgit/src/org/eclipse/jgit/transport/PacketLineIn.java
index 29fe831ae4..bc1e48e4ea 100644
--- a/org.eclipse.jgit/src/org/eclipse/jgit/transport/PacketLineIn.java
+++ b/org.eclipse.jgit/src/org/eclipse/jgit/transport/PacketLineIn.java
@@ -1,6 +1,6 @@
 /*
  * Copyright (C) 2008-2009, Google Inc.
- * Copyright (C) 2008, Robin Rosenberg <robin.rosenberg@dewire.com>
+ * Copyright (C) 2008-2009, Robin Rosenberg <robin.rosenberg@dewire.com>
  * Copyright (C) 2008, Shawn O. Pearce <spearce@spearce.org>
  * and other copyright owners as documented in the project's IP log.
  *
@@ -52,7 +52,7 @@
 import org.eclipse.jgit.lib.Constants;
 import org.eclipse.jgit.lib.MutableObjectId;
 import org.eclipse.jgit.lib.ProgressMonitor;
-import org.eclipse.jgit.util.NB;
+import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.RawParseUtils;
 
 class PacketLineIn {
@@ -105,7 +105,7 @@ String readString() throws IOException {
 			return "";
 
 		final byte[] raw = new byte[len];
-		NB.readFully(in, raw, 0, len);
+		IO.readFully(in, raw, 0, len);
 		if (raw[len - 1] == '\n')
 			len--;
 		return RawParseUtils.decode(Constants.CHARSET, raw, 0, len);
@@ -119,12 +119,12 @@ String readStringRaw() throws IOException {
 		len -= 4; // length header (4 bytes)
 
 		final byte[] raw = new byte[len];
-		NB.readFully(in, raw, 0, len);
+		IO.readFully(in, raw, 0, len);
 		return RawParseUtils.decode(Constants.CHARSET, raw, 0, len);
 	}
 
 	int readLength() throws IOException {
-		NB.readFully(in, lenbuffer, 0, 4);
+		IO.readFully(in, lenbuffer, 0, 4);
 		try {
 			final int len = RawParseUtils.parseHexInt16(lenbuffer, 0);
 			if (len != 0 && len < 4)
diff --git a/org.eclipse.jgit/src/org/eclipse/jgit/transport/SideBandInputStream.java b/org.eclipse.jgit/src/org/eclipse/jgit/transport/SideBandInputStream.java
index 7a0765030f..0a20a3c670 100644
--- a/org.eclipse.jgit/src/org/eclipse/jgit/transport/SideBandInputStream.java
+++ b/org.eclipse.jgit/src/org/eclipse/jgit/transport/SideBandInputStream.java
@@ -53,7 +53,7 @@
 import org.eclipse.jgit.errors.TransportException;
 import org.eclipse.jgit.lib.Constants;
 import org.eclipse.jgit.lib.ProgressMonitor;
-import org.eclipse.jgit.util.NB;
+import org.eclipse.jgit.util.IO;
 import org.eclipse.jgit.util.RawParseUtils;
 
 /**
@@ -229,7 +229,7 @@ private boolean doProgressLine(final String msg) {
 
 	private String readString(final int len) throws IOException {
 		final byte[] raw = new byte[len];
-		NB.readFully(in, raw, 0, len);
+		IO.readFully(in, raw, 0, len);
 		return RawParseUtils.decode(Constants.CHARSET, raw, 0, len);
 	}
 }
diff --git a/org.eclipse.jgit/src/org/eclipse/jgit/transport/WalkRemoteObjectDatabase.java b/org.eclipse.jgit/src/org/eclipse/jgit/transport/WalkRemoteObjectDatabase.java
index 6a557dfd3f..150db70c91 100644
--- a/org.eclipse.jgit/src/org/eclipse/jgit/transport/WalkRemoteObjectDatabase.java
+++ b/org.eclipse.jgit/src/org/eclipse/jgit/transport/WalkRemoteObjectDatabase.java
@@ -59,7 +59,7 @@
 import org.eclipse.jgit.lib.ObjectId;
 import org.eclipse.jgit.lib.ProgressMonitor;
 import org.eclipse.jgit.lib.Ref;
-import org.eclipse.jgit.util.NB;
+import org.eclipse.jgit.util.IO;
 
 /**
  * Transfers object data through a dumb transport.
@@ -495,7 +495,7 @@ byte[] toArray() throws IOException {
 			try {
 				if (length >= 0) {
 					final byte[] r = new byte[(int) length];
-					NB.readFully(in, r, 0, r.length);
+					IO.readFully(in, r, 0, r.length);
 					return r;
 				}
 
diff --git a/org.eclipse.jgit/src/org/eclipse/jgit/util/IO.java b/org.eclipse.jgit/src/org/eclipse/jgit/util/IO.java
new file mode 100644
index 0000000000..09280712f7
--- /dev/null
+++ b/org.eclipse.jgit/src/org/eclipse/jgit/util/IO.java
@@ -0,0 +1,198 @@
+/*
+ * Copyright (C) 2008-2009, Google Inc.
+ * Copyright (C) 2009, Robin Rosenberg <robin.rosenberg@dewire.com>
+ * Copyright (C) 2006-2008, Shawn O. Pearce <spearce@spearce.org>
+ * and other copyright owners as documented in the project's IP log.
+ *
+ * This program and the accompanying materials are made available
+ * under the terms of the Eclipse Distribution License v1.0 which
+ * accompanies this distribution, is reproduced below, and is
+ * available at http://www.eclipse.org/org/documents/edl-v10.php
+ *
+ * All rights reserved.
+ *
+ * Redistribution and use in source and binary forms, with or
+ * without modification, are permitted provided that the following
+ * conditions are met:
+ *
+ * - Redistributions of source code must retain the above copyright
+ *   notice, this list of conditions and the following disclaimer.
+ *
+ * - Redistributions in binary form must reproduce the above
+ *   copyright notice, this list of conditions and the following
+ *   disclaimer in the documentation and/or other materials provided
+ *   with the distribution.
+ *
+ * - Neither the name of the Eclipse Foundation, Inc. nor the
+ *   names of its contributors may be used to endorse or promote
+ *   products derived from this software without specific prior
+ *   written permission.
+ *
+ * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
+ * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
+ * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
+ * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
+ * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
+ * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
+ * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
+ * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
+ * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
+ * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
+ * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
+ * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.eclipse.jgit.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/** 
 * Input/Output utilities
 */
public class IO {

	/**
	 * Read an entire local file into memory as a byte array.
	 *
	 * @param path
	 *            location of the file to read.
	 * @return complete contents of the requested local file.
	 * @throws FileNotFoundException
	 *             the file does not exist.
	 * @throws IOException
	 *             the file exists, but its contents cannot be read.
	 */
	public static final byte[] readFully(final File path)
			throws FileNotFoundException, IOException {
		return IO.readFully(path, Integer.MAX_VALUE);
	}

	/**
	 * Read an entire local file into memory as a byte array.
	 *
	 * @param path
	 *            location of the file to read.
	 * @param max
	 *            maximum number of bytes to read, if the file is larger than
	 *            this limit an IOException is thrown.
	 * @return complete contents of the requested local file.
	 * @throws FileNotFoundException
	 *             the file does not exist.
	 * @throws IOException
	 *             the file exists, but its contents cannot be read.
	 */
	public static final byte[] readFully(final File path, final int max)
			throws FileNotFoundException, IOException {
		final FileInputStream in = new FileInputStream(path);
		try {
			final long sz = in.getChannel().size();
			if (sz > max)
				throw new IOException("File is too large: " + path);
			final byte[] buf = new byte[(int) sz];
			IO.readFully(in, buf, 0, buf.length);
			return buf;
		} finally {
			try {
				in.close();
			} catch (IOException ignored) {
				// ignore any close errors, this was a read only stream
			}
		}
	}

	/**
	 * Read the entire byte array into memory, or throw an exception.
	 * 
	 * @param fd
	 *            input stream to read the data from.
	 * @param dst
	 *            buffer that must be fully populated, [off, off+len).
	 * @param off
	 *            position within the buffer to start writing to.
	 * @param len
	 *            number of bytes that must be read.
	 * @throws EOFException
	 *             the stream ended before dst was fully populated.
	 * @throws IOException
	 *             there was an error reading from the stream.
	 */
	public static void readFully(final InputStream fd, final byte[] dst,
			int off, int len) throws IOException {
		while (len > 0) {
			final int r = fd.read(dst, off, len);
			if (r <= 0)
				throw new EOFException("Short read of block.");
			off += r;
			len -= r;
		}
	}

	/**
	 * Read the entire byte array into memory, or throw an exception.
	 *
	 * @param fd
	 *            file to read the data from.
	 * @param pos
	 *            position to read from the file at.
	 * @param dst
	 *            buffer that must be fully populated, [off, off+len).
	 * @param off
	 *            position within the buffer to start writing to.
	 * @param len
	 *            number of bytes that must be read.
	 * @throws EOFException
	 *             the stream ended before dst was fully populated.
	 * @throws IOException
	 *             there was an error reading from the stream.
	 */
	public static void readFully(final FileChannel fd, long pos,
			final byte[] dst, int off, int len) throws IOException {
		while (len > 0) {
			final int r = fd.read(ByteBuffer.wrap(dst, off, len), pos);
			if (r <= 0)
				throw new EOFException("Short read of block.");
			pos += r;
			off += r;
			len -= r;
		}
	}

	/**
	 * Skip an entire region of an input stream.
	 * <p>
	 * The input stream's position is moved forward by the number of requested
	 * bytes, discarding them from the input. This method does not return until
	 * the exact number of bytes requested has been skipped.
	 *
	 * @param fd
	 *            the stream to skip bytes from.
	 * @param toSkip
	 *            total number of bytes to be discarded. Must be >= 0.
	 * @throws EOFException
	 *             the stream ended before the requested number of bytes were
	 *             skipped.
	 * @throws IOException
	 *             there was an error reading from the stream.
	 */
	public static void skipFully(final InputStream fd, long toSkip)
			throws IOException {
		while (toSkip > 0) {
			final long r = fd.skip(toSkip);
			if (r <= 0)
				throw new EOFException("Short skip of block");
			toSkip -= r;
		}
	}

	private IO() {
		// Don't create instances of a static only utility.
	}
}

/*
 * Copyright (C) 2008-2009, Google Inc.
 * Copyright (C) 2008, Shawn O. Pearce <spearce@spearce.org>
 * and other copyright owners as documented in the project's IP log.
 *
@ -44,150 +43,9 @@

package org.eclipse.jgit.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/** Conversion utilities for network byte order handling. */
 public final class NB {
-	/**
-	 * Read an entire local file into memory as a byte array.
-	 *
-	 * @param path
-	 *            location of the file to read.
-	 * @return complete contents of the requested local file.
-	 * @throws FileNotFoundException
-	 *             the file does not exist.
-	 * @throws IOException
-	 *             the file exists, but its contents cannot be read.
-	 */
-	/**
-	 * Read an entire local file into memory as a byte array.
-	 *
-	 * @param path
-	 *            location of the file to read.
-	 * @param max
-	 *            maximum number of bytes to read, if the file is larger than
-	 *            this limit an IOException is thrown.
-	 * @return complete contents of the requested local file.
-	 * @throws FileNotFoundException
-	 *             the file does not exist.
-	 * @throws IOException
-	 *             the file exists, but its contents cannot be read.
-	 */
 	/**
 	 * Compare a 32 bit unsigned integer stored in a 32 bit signed integer.
 	 * <p>
diff --git a/org.eclipse.jgit/src/org/eclipse/jgit/util/TemporaryBuffer.java b/org.eclipse.jgit/src/org/eclipse/jgit/util/TemporaryBuffer.java
index 9c6addebd8..bcd858e74a 100644
--- a/org.eclipse.jgit/src/org/eclipse/jgit/util/TemporaryBuffer.java
+++ b/org.eclipse.jgit/src/org/eclipse/jgit/util/TemporaryBuffer.java
@@ -247,7 +247,7 @@ public byte[] toByteArray() throws IOException {
 		} else {
 			final FileInputStream in = new FileInputStream(onDiskFile);
 			try {
-				NB.readFully(in, out, 0, (int) len);
+				IO.readFully(in, out, 0, (int) len);
 			} finally {
 				in.close();
 			}
