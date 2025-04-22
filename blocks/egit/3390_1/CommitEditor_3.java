import org.eclipse.egit.ui.internal.commit.command.CreateBranchHandler;
import org.eclipse.egit.ui.internal.commit.command.CreateTagHandler;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.events.RefsChangedEvent;
import org.eclipse.jgit.events.RefsChangedListener;
import org.eclipse.jgit.lib.Repository;
