      Matcher matcher = VERSION_PATTERN.matcher(raw);
      if (matcher.matches() && matcher.groupCount() == 3) {
        major = Integer.parseInt(matcher.group(1));
        minor = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 0;
        bugfix = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;
      } else {
        throw new IllegalArgumentException(
            "Expected a version string starting with X[.Y[.Z]], was " + raw);
      }
