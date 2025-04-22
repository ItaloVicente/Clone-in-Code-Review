            String s1 = arg0.getLabel();
            String s2 = arg1.getLabel();
            return collator.compare(s1, s2);
        }
    };

    /**
     * Constructs a new instance of <code>OpenWithMenu</code>.
     *
     * @param page the page where the editor is opened if an item within
     *		the menu is selected
     * @deprecated As there is no way to set the file with this constructor use a
     * different constructor.
     */
    @Deprecated
