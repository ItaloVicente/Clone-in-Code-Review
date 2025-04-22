import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.events.ConfigChangedEvent;
import org.eclipse.jgit.events.ConfigChangedListener;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.lib.ConfigConstants;
