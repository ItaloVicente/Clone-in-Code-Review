import org.eclipse.egit.core.internal.util.ProjectUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.RebaseCommand.InteractiveHandler;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IllegalTodoFileModification;
import org.eclipse.jgit.lib.RebaseTodoLine;
