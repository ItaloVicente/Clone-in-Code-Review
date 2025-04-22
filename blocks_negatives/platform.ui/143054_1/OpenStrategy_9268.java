                /*ISSUE: May have to create a interface with method:
                 setSelection(Point p) so that user's custom widgets
                 can use this class. If we keep this option. */
                if (w instanceof Tree) {
                    Tree tree = (Tree) w;
                    TreeItem item = tree.getItem(new Point(e.x, e.y));
                    if (item != null) {
