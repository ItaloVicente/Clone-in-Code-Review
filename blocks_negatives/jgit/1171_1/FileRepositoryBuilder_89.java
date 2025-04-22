import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CorruptObjectException;

/** Reads a deltified object which uses an offset to find its base. */
class DeltaOfsPackedObjectLoader extends DeltaPackedObjectLoader {
	private final long deltaBase;

	DeltaOfsPackedObjectLoader(final PackFile pr, final long objectOffset,
			final int headerSz, final int deltaSz, final long base) {
		super(pr, objectOffset, headerSz, deltaSz);
		deltaBase = base;
	}

	protected PackedObjectLoader getBaseLoader(final WindowCursor curs)
			throws IOException {
		return pack.resolveBase(curs, deltaBase);
	}

	@Override
	public int getRawType() {
		return Constants.OBJ_OFS_DELTA;
	}
