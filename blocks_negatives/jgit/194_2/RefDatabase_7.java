import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

class RefDatabase {
	private static final String REFS_SLASH = "refs/";

	private static final String[] refSearchPaths = { "", REFS_SLASH,
			R_TAGS, Constants.R_HEADS, Constants.R_REMOTES };

	private final Repository db;

	private final File gitDir;

	private final File refsDir;

	private Map<String, Ref> looseRefs;
	private Map<String, Long> looseRefsMTime;
	private Map<String, String> looseSymRefs;

	private final File packedRefsFile;

	private Map<String, Ref> packedRefs;

	private long packedRefsLastModified;

	private long packedRefsLength;

	int lastRefModification;
