
package org.eclipse.jgit.pgm.debug;

import static java.lang.Integer.valueOf;
import static java.lang.Long.valueOf;

import java.io.File;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.NB;
import org.kohsuke.args4j.Option;

@Command(usage = "usage_TextHashFunctions")
class TextHashFunctions extends TextBuiltin {

	final Hash sha1 = new Hash() {
		private final MessageDigest md = Constants.newMessageDigest();

		@Override
		protected int hashRegion(byte[] raw
			md.reset();
			md.update(raw
			return NB.decodeInt32(md.digest()
		}
	};

	final Hash djb = new Hash() {
		@Override
		protected int hashRegion(byte[] raw
			int hash = 5381;
			for (; ptr < end; ptr++)
				hash = ((hash << 5) + hash) + (raw[ptr] & 0xff);
			return hash;
		}
	};

	final Hash string_hash31 = new Hash() {
		@Override
		protected int hashRegion(byte[] raw
			int hash = 0;
			for (; ptr < end; ptr++)
				hash = 31 * hash + (raw[ptr] & 0xff);
			return hash;
		}
	};

	final Hash rabin_DeltaIndex = new Hash() {
		private final byte[] buf16 = new byte[16];

		@Override
		protected int hashRegion(byte[] raw
			if (end - ptr < 16) {
				Arrays.fill(buf16
				System.arraycopy(raw
				return rabin(buf16
			} else {
				return rabin(raw
			}
		}

		private int rabin(byte[] raw
			int hash;

					| (raw[ptr + 3] & 0xff);
			hash ^= T[hash >>> 31];

			hash = ((hash << 8) | (raw[ptr + 4] & 0xff)) ^ T[hash >>> 23];
			hash = ((hash << 8) | (raw[ptr + 5] & 0xff)) ^ T[hash >>> 23];
			hash = ((hash << 8) | (raw[ptr + 6] & 0xff)) ^ T[hash >>> 23];
			hash = ((hash << 8) | (raw[ptr + 7] & 0xff)) ^ T[hash >>> 23];

			hash = ((hash << 8) | (raw[ptr + 8] & 0xff)) ^ T[hash >>> 23];
			hash = ((hash << 8) | (raw[ptr + 9] & 0xff)) ^ T[hash >>> 23];
			hash = ((hash << 8) | (raw[ptr + 10] & 0xff)) ^ T[hash >>> 23];
			hash = ((hash << 8) | (raw[ptr + 11] & 0xff)) ^ T[hash >>> 23];

			hash = ((hash << 8) | (raw[ptr + 12] & 0xff)) ^ T[hash >>> 23];
			hash = ((hash << 8) | (raw[ptr + 13] & 0xff)) ^ T[hash >>> 23];
			hash = ((hash << 8) | (raw[ptr + 14] & 0xff)) ^ T[hash >>> 23];
			hash = ((hash << 8) | (raw[ptr + 15] & 0xff)) ^ T[hash >>> 23];

			return hash;
		}

		private final int[] T = { 0x00000000
				0xa98d665a
				0x5ca23386
				0xa6359968
				0x10c90156
				0xea5eabb8
				0x1f71fe64
				0xdb05a842
				0x5cd9d7db
				0xfaec4eb3
				0x6241cf4e
				0x98d665a0
				0x43a829bf
				0xb93f8351
				0x31d72f1d
				0x97e2b675
				0x103ec9ec
				0xdb8984a5
				0x2ea6d179
				0xd4317b97
				0x6d0ef8c6
				0x97995228
				0x62b607f4
				0xa9014abd
				0x2edd3524
				0x88e8ac4c
				0x3f0c6dbc
				0xc59bc752
				0x1ee58b4d
				0xe47221a3
				0x7cdfa05e
				0xdaea3936
				0x5d3646af
				0x99421089
				0x6c6d4555
				0x96faefbb
				0x20067785
				0xda91dd6b
				0x2fbe88b7
				0xf44ce84f
				0x739097d6
				0xd5a50ebe
				0x4d088f43
				0xb79f25ad
				0x6ce169b2
				0x9676c35c
				0x011859ce
				0xa72dc0a6
				0x20f1bf3f
				0xeb46f276
				0x1e69a7aa
				0xe4fe0d44
	};

	final Fold truncate = new Fold() {
		@Override
		public int fold(int hash
			return hash & ((1 << bits) - 1);
		}
	};

	final Fold golden_ratio = new Fold() {
		@Override
		public int fold(int hash
			return (hash * 0x9e370001) >>> (32 - bits);
		}
	};


	@Option(name = "--hash"
	List<String> hashFunctions = new ArrayList<>();

	@Option(name = "--fold"
	List<String> foldFunctions = new ArrayList<>();

	@Option(name = "--text-limit"

	@Option(name = "--repository"
	List<File> gitDirs = new ArrayList<>();

	@Override
	protected boolean requiresRepository() {
		return false;
	}

	@Override
	protected void run() throws Exception {
		if (gitDirs.isEmpty()) {
					.findGitDir();
			if (rb.getGitDir() == null)
				throw die(CLIText.get().cantFindGitDirectory);
			gitDirs.add(rb.getGitDir());
		}

		for (File dir : gitDirs) {
			RepositoryBuilder rb = new RepositoryBuilder();
			if (RepositoryCache.FileKey.isGitRepository(dir
				rb.setGitDir(dir);
			else
				rb.findGitDir(dir);

			try (Repository repo = rb.build()) {
				run(repo);
			}
		}
	}

	private void run(Repository repo) throws Exception {
		List<Function> all = init();

		long fileCnt = 0;
		long lineCnt = 0;
		try (ObjectReader or = repo.newObjectReader();
			RevWalk rw = new RevWalk(or);
			TreeWalk tw = new TreeWalk(or)) {
			final MutableObjectId id = new MutableObjectId();
			tw.reset(rw.parseTree(repo.resolve(Constants.HEAD)));
			tw.setRecursive(true);

			while (tw.next()) {
				FileMode fm = tw.getFileMode(0);
				if (!FileMode.REGULAR_FILE.equals(fm)
						&& !FileMode.EXECUTABLE_FILE.equals(fm))
					continue;

				byte[] raw;
				try {
					tw.getObjectId(id
					raw = or.open(id).getCachedBytes(textLimit * 1024);
				} catch (LargeObjectException tooBig) {
					continue;
				}

				if (RawText.isBinary(raw))
					continue;

				RawText txt = new RawText(raw);
				int[] lines = new int[txt.size()];
				int cnt = 0;
				HashSet<Line> u = new HashSet<>();
				for (int i = 0; i < txt.size(); i++) {
					if (u.add(new Line(txt
						lines[cnt++] = i;
				}

				fileCnt++;
				lineCnt += cnt;

				for (Function fun : all)
					testOne(fun
			}
		}

		File directory = repo.getDirectory();
		if (directory != null) {
			String name = directory.getName();
			File parent = directory.getParentFile();
			if (name.equals(Constants.DOT_GIT) && parent != null)
				name = parent.getName();
		}
		outw.format("  %6d files; %5d avg. unique lines/file\n"
				valueOf(fileCnt)
				valueOf(lineCnt / fileCnt));
		outw.format("%-20s %-15s %9s\n"
		String lastHashName = null;
		for (Function fun : all) {
			String hashName = fun.hash.name;
			if (hashName.equals(lastHashName))
			outw.format("%-20s %-15s %9d\n"
					hashName
					fun.fold.name
					valueOf(fun.maxChainLength));
			lastHashName = fun.hash.name;
		}
		outw.println();
		outw.flush();
	}

	private static void testOne(Function fun
			int cnt) {
		final Hash cmp = fun.hash;
		final Fold fold = fun.fold;

		final int bits = tableBits(cnt);
		final int[] buckets = new int[1 << bits];
		for (int i = 0; i < cnt; i++)
			buckets[fold.fold(cmp.hash(txt

		int maxChainLength = 0;
		for (int i = 0; i < buckets.length; i++)
			maxChainLength = Math.max(maxChainLength
		fun.maxChainLength = Math.max(fun.maxChainLength
	}

	private List<Function> init() {
		List<Hash> hashes = new ArrayList<>();
		List<Fold> folds = new ArrayList<>();

		try {
			for (Field f : TextHashFunctions.class.getDeclaredFields()) {
				if (f.getType() == Hash.class) {
					f.setAccessible(true);
					Hash cmp = (Hash) f.get(this);
					cmp.name = f.getName();
					hashes.add(cmp);

				} else if (f.getType() == Fold.class) {
					f.setAccessible(true);
					Fold fold = (Fold) f.get(this);
					fold.name = f.getName();
					folds.add(fold);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Cannot determine names"
		}

		List<Function> all = new ArrayList<>();
		for (Hash cmp : hashes) {
			if (include(cmp.name
				for (Fold f : folds) {
					if (include(f.name
						all.add(new Function(cmp
					}
				}
			}
		}
		return all;
	}

	private static boolean include(String name
		if (want.isEmpty())
			return true;
		for (String s : want) {
			if (s.equalsIgnoreCase(name))
				return true;
		}
		return false;
	}

	private static class Function {
		final Hash hash;

		final Fold fold;

		int maxChainLength;

		Function(Hash cmp
			this.hash = cmp;
			this.fold = fold;
		}
	}

	private static abstract class Hash extends RawTextComparator {
		String name;

		@Override
		public boolean equals(RawText a
			return RawTextComparator.DEFAULT.equals(a
		}
	}

	private static abstract class Fold {
		String name;

		abstract int fold(int hash
	}

	private static class Line {
		private final RawText txt;

		private final int pos;

		Line(RawText txt
			this.txt = txt;
			this.pos = pos;
		}

		@Override
		public int hashCode() {
			return RawTextComparator.DEFAULT.hash(txt
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Line) {
				Line e = (Line) obj;
				return RawTextComparator.DEFAULT.equals(txt
			}
			return false;
		}
	}

	private static int tableBits(int sz) {
		int bits = 31 - Integer.numberOfLeadingZeros(sz);
		if (bits == 0)
			bits = 1;
		if (1 << bits < sz)
			bits++;
		return bits;
	}
}
