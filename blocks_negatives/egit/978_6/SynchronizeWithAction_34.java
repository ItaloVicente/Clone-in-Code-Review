import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.synchronize.GitSynchronize;
import org.eclipse.egit.ui.internal.synchronize.SelectSynchronizeResourceDialog;
import org.eclipse.egit.ui.internal.synchronize.SyncRepoEntity;
import org.eclipse.egit.ui.internal.synchronize.SyncRepoEntity.SyncRefEntity;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteConfig;

