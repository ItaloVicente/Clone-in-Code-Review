                if (fFilteredList.isEmpty()) {
                    handleEmptyList();
                } else {
                    validateCurrentSelection();
                    fFilterText.selectAll();
                    fFilterText.setFocus();
                }
            }
        });
