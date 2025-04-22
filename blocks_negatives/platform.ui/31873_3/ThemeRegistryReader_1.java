        } else if (elementName.equals(IWorkbenchRegistryConstants.TAG_CATEGORYPRESENTATIONBINDING)) {
            String categoryId = element.getAttribute(IWorkbenchRegistryConstants.ATT_CATEGORY_ID);
            String presentationId = element.getAttribute(IWorkbenchRegistryConstants.ATT_PRESENTATIONID);
            themeRegistry.addCategoryPresentationBinding(categoryId,
                    presentationId);
            return true;
        }
