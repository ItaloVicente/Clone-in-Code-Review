            }
        });
    }

    /**
     * Modify the argument array to be sorted by version. If the reverse
     * boolean is true, the array is assumed to already be sorted and the
     * direction of sort (ascending vs. descending) is reversed.  Entries
     * with the same name are sorted by name.
     * 
     * @param reverse
     *            if true then the order of the argument is reversed without
     *            examining the value of the fields
     * @param infos
     *            the data to be sorted
     */
    public static void sortByVersion(boolean reverse, AboutData[] infos) {
        if (reverse) {
            reverse(infos);
            return;
        }
