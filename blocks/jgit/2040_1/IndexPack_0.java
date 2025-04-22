import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.lib.objectcheckers.IObjectChecker;
import org.eclipse.jgit.storage.file.PackIndexWriter;
import org.eclipse.jgit.storage.file.PackLock;
import org.eclipse.jgit.storage.pack.BinaryDelta;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.io.NullOutputStream;

import java.io.*;
import java.security.DigestOutputStream;
