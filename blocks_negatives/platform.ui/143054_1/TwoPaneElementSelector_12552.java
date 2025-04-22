            qualifiers[i] = text;
        }

        TwoArrayQuickSorter sorter;
        if (fLowerListComparator == null) {
        	sorter = new TwoArrayQuickSorter(isCaseIgnored());
        } else {
        	sorter = new TwoArrayQuickSorter(fLowerListComparator);
        }

        sorter.sort(qualifiers, elements);
        for (int i = 0; i != length; i++) {
            TableItem item = new TableItem(fLowerList, SWT.NONE);
            item.setText(qualifiers[i]);
            item.setImage(fQualifierRenderer.getImage(elements[i]));
        }
        if (fLowerList.getItemCount() > 0) {
