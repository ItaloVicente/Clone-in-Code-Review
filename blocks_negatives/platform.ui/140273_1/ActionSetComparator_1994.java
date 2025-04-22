        if (e1 instanceof IActionSetDescriptor) {
            String str1 = DialogUtil.removeAccel(((IActionSetDescriptor) e1)
                    .getLabel());
            String str2 = DialogUtil.removeAccel(((IActionSetDescriptor) e2)
                    .getLabel());
            return getComparator().compare(str1, str2);
        }
        return 0;
    }
