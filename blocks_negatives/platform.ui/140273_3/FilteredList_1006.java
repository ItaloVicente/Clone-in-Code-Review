            int itemCount = fTable.getItemCount();

            if (fCount < itemCount) {
                fTable.setRedraw(false);
                fTable.remove(fCount, itemCount - 1);
                fTable.setRedraw(true);
                itemCount = fTable.getItemCount();
            }
            if (fCount == 0) {
                fTable.notifyListeners(SWT.Selection, new Event());
                return Status.OK_STATUS;
            }
            int iterations = Math.min(10, fCount - currentIndex);
            for (int i = 0; i < iterations; i++) {
                if (monitor.isCanceled()) {
