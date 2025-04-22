import org.eclipse.jgit.diffmergetool.DiffToolManager;
import org.eclipse.jgit.diffmergetool.MergeToolManager;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.events.ConfigChangedEvent;
import org.eclipse.jgit.events.ConfigChangedListener;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
