    /**
     * Constructs a new source viewer. The vertical ruler is initially visible.
     * The viewer has not yet been initialized with a source viewer configuration.
     *
     * @param parent the parent of the viewer's control
     * @param ruler the vertical ruler used by this source viewer
     * @param styles the SWT style bits
     */
    public ExtendedSourceViewer(Composite parent, IVerticalRuler ruler,
            int styles) {
        super(parent, ruler, styles);
    }
