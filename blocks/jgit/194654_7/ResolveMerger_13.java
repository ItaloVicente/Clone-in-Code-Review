  private int mergeFileModes(int modeB
    if (modeO == modeT) {
      return modeO;
    }
    if (modeB == modeO)
    {
      return (modeT == FileMode.MISSING.getBits()) ? modeO : modeT;
    }
    if (modeB == modeT)
    {
      return (modeO == FileMode.MISSING.getBits()) ? modeT : modeO;
    }
    return FileMode.MISSING.getBits();
  }

  private RawText getRawText(ObjectId id
      Attributes attributes)
      throws IOException
    if (id.equals(ObjectId.zeroId())) {
      return new RawText(new byte[]{});
    }

    ObjectLoader loader = LfsFactory.getInstance().applySmudgeFilter(
        getRepository()
        attributes.get(Constants.ATTR_MERGE));
    int threshold = PackConfig.DEFAULT_BIG_FILE_THRESHOLD;
    return RawText.load(loader
  }

  private static boolean nonTree(int mode) {
    return mode != 0 && !FileMode.TREE.equals(mode);
  }

  private static boolean isGitLink(int mode) {
    return FileMode.GITLINK.equals(mode);
  }
