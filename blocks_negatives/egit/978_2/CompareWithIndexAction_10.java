import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.internal.storage.GitFileRevision;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.EditableRevision;
import org.eclipse.egit.ui.internal.GitCompareFileRevisionEditorInput;
import org.eclipse.jface.action.IAction;
import org.eclipse.jgit.lib.GitIndex;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;

