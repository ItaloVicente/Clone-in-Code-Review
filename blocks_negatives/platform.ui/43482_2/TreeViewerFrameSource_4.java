        } else {
            TreeFrame frame = createFrame(parent);
            if ((flags & IFrameSource.FULL_CONTEXT) != 0) {
                frame.setSelection(viewer.getSelection());
                Object[] expanded = viewer.getExpandedElements();
                Object[] newExpanded = new Object[expanded.length + 1];
                System.arraycopy(expanded, 0, newExpanded, 0, expanded.length);
                newExpanded[newExpanded.length - 1] = input;
                frame.setExpandedElements(newExpanded);
            }
            return frame;
