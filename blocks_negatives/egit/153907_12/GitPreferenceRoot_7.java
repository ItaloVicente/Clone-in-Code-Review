import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.events.ConfigChangedEvent;
import org.eclipse.jgit.events.ConfigChangedListener;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.internal.diffmergetool.DiffTools;
import org.eclipse.jgit.internal.diffmergetool.MergeTools;
import org.eclipse.jgit.storage.file.FileBasedConfig;
