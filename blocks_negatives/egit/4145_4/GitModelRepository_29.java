public class GitModelRepository extends GitModelObject {

	private final Repository repo;

	private final RevCommit srcRev;

	private final RevCommit dstRev;

	private final Set<IProject> projects;
