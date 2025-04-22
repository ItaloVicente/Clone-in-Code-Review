package org.eclipse.jgit.api;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.util.FS;

public class Git implements AutoCloseable {
	private final Repository repo;

	private final boolean closeRepo;

	public static Git open(File dir) throws IOException {
		return open(dir
	}

	public static Git open(File dir
		RepositoryCache.FileKey key;

		key = RepositoryCache.FileKey.lenient(dir
		Repository db = new RepositoryBuilder().setFS(fs).setGitDir(key.getFile())
				.setMustExist(true).build();
		return new Git(db
	}

	public static Git wrap(Repository repo) {
		return new Git(repo);
	}

	@Override
	public void close() {
		if (closeRepo)
			repo.close();
	}

	public static CloneCommand cloneRepository() {
		return new CloneCommand();
	}

	public static LsRemoteCommand lsRemoteRepository() {
		return new LsRemoteCommand(null);
	}

	public static InitCommand init() {
		return new InitCommand();
	}

	public Git(Repository repo) {
		this(repo
	}

	Git(Repository repo
		this.repo = requireNonNull(repo);
		this.closeRepo = closeRepo;
	}

	public CommitCommand commit() {
		return new CommitCommand(repo);
	}

	public LogCommand log() {
		return new LogCommand(repo);
	}

	public MergeCommand merge() {
		return new MergeCommand(repo);
	}

	public PullCommand pull() {
		return new PullCommand(repo);
	}

	public CreateBranchCommand branchCreate() {
		return new CreateBranchCommand(repo);
	}

	public DeleteBranchCommand branchDelete() {
		return new DeleteBranchCommand(repo);
	}

	public ListBranchCommand branchList() {
		return new ListBranchCommand(repo);
	}

	public ListTagCommand tagList() {
		return new ListTagCommand(repo);
	}

	public RenameBranchCommand branchRename() {
		return new RenameBranchCommand(repo);
	}

	public AddCommand add() {
		return new AddCommand(repo);
	}

	public TagCommand tag() {
		return new TagCommand(repo);
	}

	public FetchCommand fetch() {
		return new FetchCommand(repo);
	}

	public PushCommand push() {
		return new PushCommand(repo);
	}

	public CherryPickCommand cherryPick() {
		return new CherryPickCommand(repo);
	}

	public RevertCommand revert() {
		return new RevertCommand(repo);
	}

	public RebaseCommand rebase() {
		return new RebaseCommand(repo);
	}

	public RmCommand rm() {
		return new RmCommand(repo);
	}

	public CheckoutCommand checkout() {
		return new CheckoutCommand(repo);
	}

	public ResetCommand reset() {
		return new ResetCommand(repo);
	}

	public StatusCommand status() {
		return new StatusCommand(repo);
	}

	public ArchiveCommand archive() {
		return new ArchiveCommand(repo);
	}

	public AddNoteCommand notesAdd() {
		return new AddNoteCommand(repo);
	}

	public RemoveNoteCommand notesRemove() {
		return new RemoveNoteCommand(repo);
	}

	public ListNotesCommand notesList() {
		return new ListNotesCommand(repo);
	}

	public ShowNoteCommand notesShow() {
		return new ShowNoteCommand(repo);
	}

	public LsRemoteCommand lsRemote() {
		return new LsRemoteCommand(repo);
	}

	public CleanCommand clean() {
		return new CleanCommand(repo);
	}

	public BlameCommand blame() {
		return new BlameCommand(repo);
	}

	public ReflogCommand reflog() {
		return new ReflogCommand(repo);
	}

	public DiffCommand diff() {
		return new DiffCommand(repo);
	}

	public DeleteTagCommand tagDelete() {
		return new DeleteTagCommand(repo);
	}

	public SubmoduleAddCommand submoduleAdd() {
		return new SubmoduleAddCommand(repo);
	}

	public SubmoduleInitCommand submoduleInit() {
		return new SubmoduleInitCommand(repo);
	}

	public SubmoduleDeinitCommand submoduleDeinit() {
		return new SubmoduleDeinitCommand(repo);
	}

	public SubmoduleStatusCommand submoduleStatus() {
		return new SubmoduleStatusCommand(repo);
	}

	public SubmoduleSyncCommand submoduleSync() {
		return new SubmoduleSyncCommand(repo);
	}

	public SubmoduleUpdateCommand submoduleUpdate() {
		return new SubmoduleUpdateCommand(repo);
	}

	public StashListCommand stashList() {
		return new StashListCommand(repo);
	}

	public StashCreateCommand stashCreate() {
		return new StashCreateCommand(repo);
	}

	public StashApplyCommand stashApply() {
		return new StashApplyCommand(repo);
	}

	public StashDropCommand stashDrop() {
		return new StashDropCommand(repo);
	}

	public ApplyCommand apply() {
		return new ApplyCommand(repo);
	}

	public GarbageCollectCommand gc() {
		return new GarbageCollectCommand(repo);
	}

	public NameRevCommand nameRev() {
		return new NameRevCommand(repo);
	}

	public DescribeCommand describe() {
		return new DescribeCommand(repo);
	}

	public RemoteListCommand remoteList() {
		return new RemoteListCommand(repo);
	}

	public RemoteAddCommand remoteAdd() {
		return new RemoteAddCommand(repo);
	}

	public RemoteRemoveCommand remoteRemove() {
		return new RemoteRemoveCommand(repo);
	}

	public RemoteSetUrlCommand remoteSetUrl() {
		return new RemoteSetUrlCommand(repo);
	}

	public Repository getRepository() {
		return repo;
	}

	@Override
	public String toString() {
	}
}
