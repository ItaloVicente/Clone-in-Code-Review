                prev = next;
            }
        } else {
            int prev = -1;
            for (int rowIdx = 0; rowIdx < rows; rowIdx++) {
                int next = gridInfo[cols * rowIdx + rowId];
