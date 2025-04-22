		Arrays.sort(array, new Comparator<FileEditorMapping>() {

            @Override
			public int compare(FileEditorMapping o1, FileEditorMapping o2) {
				String s1 = o1.getLabel();
				String s2 = o2.getLabel();
                return collator.compare(s1, s2);
            }
        });
