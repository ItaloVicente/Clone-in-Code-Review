
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.util.StringUtils.equalsIgnoreCase;
import static org.eclipse.jgit.util.StringUtils.toLowerCase;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.storage.file.LazyObjectIdSetFile;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectIdSet;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.SystemReader;

public class TransferConfig {

	public static final Config.SectionParser<TransferConfig> KEY =
			TransferConfig::new;

	public enum FsckMode {
		ERROR
		WARN
		IGNORE;
	}

	enum ProtocolVersion {
		V0("0")

		final String name;

		ProtocolVersion(String name) {
			this.name = name;
		}

		@Nullable
		static ProtocolVersion parse(@Nullable String name) {
			if (name == null) {
				return null;
			}
			for (ProtocolVersion v : ProtocolVersion.values()) {
				if (v.name.equals(name)) {
					return v;
				}
			}
			return null;
		}
	}

	private final boolean fetchFsck;
	private final boolean receiveFsck;
	private final String fsckSkipList;
	private final EnumSet<ObjectChecker.ErrorType> ignore;
	private final boolean allowInvalidPersonIdent;
	private final boolean safeForWindows;
	private final boolean safeForMacOS;
	private final boolean allowRefInWant;
	private final boolean allowTipSha1InWant;
	private final boolean allowReachableSha1InWant;
	private final boolean allowFilter;
	final @Nullable ProtocolVersion protocolVersion;
	final String[] hideRefs;

	public TransferConfig(Repository db) {
		this(db.getConfig());
	}

	@SuppressWarnings("nls")
	public TransferConfig(Config rc) {
		boolean fsck = rc.getBoolean("transfer"
		fetchFsck = rc.getBoolean("fetch"
		receiveFsck = rc.getBoolean("receive"
		fsckSkipList = rc.getString(FSCK
		allowInvalidPersonIdent = rc.getBoolean(FSCK
				false);
		safeForWindows = rc.getBoolean(FSCK
						SystemReader.getInstance().isWindows());
		safeForMacOS = rc.getBoolean(FSCK
						SystemReader.getInstance().isMacOS());

		ignore = EnumSet.noneOf(ObjectChecker.ErrorType.class);
		EnumSet<ObjectChecker.ErrorType> set = EnumSet
				.noneOf(ObjectChecker.ErrorType.class);
		for (String key : rc.getNames(FSCK)) {
			if (equalsIgnoreCase(key
					|| equalsIgnoreCase(key
					|| equalsIgnoreCase(key
					|| equalsIgnoreCase(key
					|| equalsIgnoreCase(key
				continue;
			}

			ObjectChecker.ErrorType id = FsckKeyNameHolder.parse(key);
			if (id != null) {
				switch (rc.getEnum(FSCK
				case ERROR:
					ignore.remove(id);
					break;
				case WARN:
				case IGNORE:
					ignore.add(id);
					break;
				}
				set.add(id);
			}
		}
		if (!set.contains(ObjectChecker.ErrorType.ZERO_PADDED_FILEMODE)
				&& rc.getBoolean(FSCK
			ignore.add(ObjectChecker.ErrorType.ZERO_PADDED_FILEMODE);
		}

		allowRefInWant = rc.getBoolean("uploadpack"
		allowTipSha1InWant = rc.getBoolean(
				"uploadpack"
		allowReachableSha1InWant = rc.getBoolean(
				"uploadpack"
		allowFilter = rc.getBoolean(
				"uploadpack"
		protocolVersion = ProtocolVersion.parse(rc.getString("protocol"
		hideRefs = rc.getStringList("uploadpack"
	}

	@Nullable
	public ObjectChecker newObjectChecker() {
		return newObjectChecker(fetchFsck);
	}

	@Nullable
	public ObjectChecker newReceiveObjectChecker() {
		return newObjectChecker(receiveFsck);
	}

	private ObjectChecker newObjectChecker(boolean check) {
		if (!check) {
			return null;
		}
		return new ObjectChecker()
			.setIgnore(ignore)
			.setAllowInvalidPersonIdent(allowInvalidPersonIdent)
			.setSafeForWindows(safeForWindows)
			.setSafeForMacOS(safeForMacOS)
			.setSkipList(skipList());
	}

	private ObjectIdSet skipList() {
		if (fsckSkipList != null && !fsckSkipList.isEmpty()) {
			return new LazyObjectIdSetFile(new File(fsckSkipList));
		}
		return null;
	}

	public boolean isAllowTipSha1InWant() {
		return allowTipSha1InWant;
	}

	public boolean isAllowReachableSha1InWant() {
		return allowReachableSha1InWant;
	}

	public boolean isAllowFilter() {
		return allowFilter;
	}

	public boolean isAllowRefInWant() {
		return allowRefInWant;
	}

	public RefFilter getRefFilter() {
		if (hideRefs.length == 0)
			return RefFilter.DEFAULT;

		return new RefFilter() {
			@Override
			public Map<String
				Map<String
				for (Map.Entry<String
					boolean add = true;
					for (String hide : hideRefs) {
						if (e.getKey().equals(hide) || prefixMatch(hide
							add = false;
							break;
						}
					}
					if (add)
						result.put(e.getKey()
				}
				return result;
			}

			private boolean prefixMatch(String p
				return p.charAt(p.length() - 1) == '/' && s.startsWith(p);
			}
		};
	}

	boolean hasDefaultRefFilter() {
		return hideRefs.length == 0;
	}

	static class FsckKeyNameHolder {
		private static final Map<String

		static {
			errors = new HashMap<>();
			for (ObjectChecker.ErrorType m : ObjectChecker.ErrorType.values()) {
				errors.put(keyNameFor(m.name())
			}
		}

		@Nullable
		static ObjectChecker.ErrorType parse(String key) {
			return errors.get(toLowerCase(key));
		}

		private static String keyNameFor(String name) {
			StringBuilder r = new StringBuilder(name.length());
			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);
				if (c != '_') {
					r.append(c);
				}
			}
			return toLowerCase(r.toString());
		}

		private FsckKeyNameHolder() {
		}
	}
}
