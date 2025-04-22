                    }
                } catch (CoreException e) {
                }
            }
            viewer.setSelection(new StructuredSelection(selectionList));
        }

        Scrollable scrollable = (Scrollable) viewer.getControl();
        ScrollBar bar = scrollable.getVerticalBar();
        if (bar != null) {
            try {
                String posStr = memento.getString(TAG_VERTICAL_POSITION);
                int position;
                position = Integer.parseInt(posStr);
                bar.setSelection(position);
            } catch (NumberFormatException e) {
            }
        }
        bar = scrollable.getHorizontalBar();
        if (bar != null) {
            try {
                String posStr = memento.getString(TAG_HORIZONTAL_POSITION);
                int position;
                position = Integer.parseInt(posStr);
                bar.setSelection(position);
            } catch (NumberFormatException e) {
            }
        }

        updateSortState();
        viewer.refresh();
    }

    @Override
