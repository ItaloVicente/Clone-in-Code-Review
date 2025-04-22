            switch (columnIndex) {
            case 0:
                return red;
            default:
                return green;
            }
        }

    }

    /**
     * A class to test color providing without coloured columns.
     */
    class ColorViewLabelProvider extends TableTestLabelProvider implements IColorProvider{
