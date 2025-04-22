import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.common.LocalRepositoryTestCase;
import org.eclipse.egit.ui.common.StagingViewTester;
import org.eclipse.egit.ui.internal.repository.RepositoriesView;
import org.eclipse.egit.ui.internal.staging.StagingView;
import org.eclipse.egit.ui.test.TestUtil;
import org.eclipse.egit.ui.test.commitmessageprovider.CommitMessageProviderFactory;
import org.eclipse.egit.ui.view.repositories.GitRepositoriesViewTestUtils;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Before;
