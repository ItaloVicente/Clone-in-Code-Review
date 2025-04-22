            }
            if (selectionIndex < 0) {
                int oldLength = currentItems.length;
                String[] newItems = new String[oldLength + 1];
                System.arraycopy(currentItems, 0, newItems, 0, oldLength);
                newItems[oldLength] = path;
                this.sourceNameField.setItems(newItems);
                selectionIndex = oldLength;
            }
            this.sourceNameField.select(selectionIndex);

            resetSelection();
        }
    }

    /**
     * Update the tree to only select those elements that match the selected types
     */
    @Override
