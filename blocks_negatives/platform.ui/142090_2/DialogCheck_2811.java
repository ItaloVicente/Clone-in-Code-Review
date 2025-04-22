            if (child instanceof TabFolder) {
                TabFolder folder = (TabFolder) child;
                int numPages = folder.getItemCount();
                for (int j = 0; j < numPages; j++) {
                    folder.setSelection(j);
                }
            }
            else if (child instanceof Button) {
