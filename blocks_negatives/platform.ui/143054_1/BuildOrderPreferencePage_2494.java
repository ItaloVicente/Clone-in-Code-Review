        this.workbench = workbench;
        setPreferenceStore(PrefUtil.getInternalPreferenceStore());
    }

    /**
     * Move the current selection in the build list down.
     */
    private void moveSelectionDown() {

        if (this.buildList.getSelectionCount() == 1) {
            int currentIndex = this.buildList.getSelectionIndex();
            if (currentIndex < this.buildList.getItemCount() - 1) {
                String elementToMove = this.buildList.getItem(currentIndex);
                this.buildList.remove(currentIndex);
                this.buildList.add(elementToMove, currentIndex + 1);
                this.buildList.select(currentIndex + 1);
            }
        }
    }

    /**
     * Move the current selection in the build list up.
     */
    private void moveSelectionUp() {

        int currentIndex = this.buildList.getSelectionIndex();

        if (currentIndex > 0 && this.buildList.getSelectionCount() == 1) {
            String elementToMove = this.buildList.getItem(currentIndex);
            this.buildList.remove(currentIndex);
            this.buildList.add(elementToMove, currentIndex - 1);
            this.buildList.select(currentIndex - 1);
        }
    }

    /**
     * Performs special processing when this page's Defaults button has been pressed.
     * In this case change the defaultOrderButton to have it's selection set to true.
     */
    @Override
