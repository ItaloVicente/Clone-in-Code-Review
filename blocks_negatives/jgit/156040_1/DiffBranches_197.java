    private final Git git;
    private final String branchA;
    private final String branchB;

    public DiffBranches(Git git,
                        String branchA,
                        String branchB) {
        this.git = checkNotNull("git",
                                git);
        this.branchA = checkNotEmpty("branchA",
                                     branchA);
        this.branchB = checkNotEmpty("branchB",
                                     branchB);
    }

    public List<FileDiff> execute() {
        final List<FileDiff> diffs = new ArrayList<>();

        final List<DiffEntry> result = git.listDiffs(git.getTreeFromRef(this.branchA),
                                                     git.getTreeFromRef(this.branchB));

        final DiffFormatter formatter = createFormatter();

        result.forEach(elem -> {
            final FileHeader header = getFileHeader(formatter,
                                                    elem);
            header.toEditList().forEach(edit -> diffs.add(createFileDiff(elem,
                                                                         header,
                                                                         edit)));
        });

        return diffs;
    }

    private FileHeader getFileHeader(final DiffFormatter formatter,
                                     final DiffEntry elem) {
        try {
            return formatter.toFileHeader(elem);
        } catch (IOException e) {
            throw new GitException("A problem occurred when trying to obtain diffs between files",
                                   e);
        }
    }

    private DiffFormatter createFormatter() {

        OutputStream outputStream = new ByteArrayOutputStream();
        DiffFormatter formatter = new DiffFormatter(outputStream);
        formatter.setRepository(git.getRepository());
        return formatter;
    }

    private FileDiff createFileDiff(final DiffEntry elem,
                                    final FileHeader header,
                                    final Edit edit) {
        try {
            final String changeType = header.getChangeType().toString();
            final int startA = edit.getBeginA();
            final int endA = edit.getEndA();
            final int startB = edit.getBeginB();
            final int endB = edit.getEndB();

            String pathA = header.getOldPath();
            String pathB = header.getNewPath();

            final List<String> linesA = getLines(elem.getOldId().toObjectId(),
                                                 startA,
                                                 endA);
            final List<String> linesB = getLines(elem.getNewId().toObjectId(),
                                                 startB,
                                                 endB);

            return new FileDiffImpl(pathA,
                                    pathB,
                                    startA,
                                    endA,
                                    startB,
                                    endB,
                                    changeType,
                                    linesA,
                                    linesB);
        } catch (IOException e) {
            throw new GitException("A problem occurred when trying to obtain diffs between files",
                                   e);
        }
    }

    private List<String> getLines(final ObjectId id,
                                  final int fromStart,
                                  final int fromEnd) throws IOException {
        List<String> lines = new ArrayList<>();
        if (!id.equals(ObjectId.zeroId())) {
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            final ObjectLoader loader = git.getRepository().open(id);
            loader.copyTo(stream);
            final String content = stream.toString();
            final List<String> filteredLines = Arrays.asList(content.split("\n"));
            lines = filteredLines.subList(fromStart,
                                          fromEnd);
        }
        return lines;
    }
