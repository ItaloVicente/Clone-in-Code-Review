 * This class is a simple parser implementing the IReadmeFileParser
 * interface. It parses a Readme file into sections based on the
 * existence of numbered section tags in the input. A line beginning
 * with a number followed by a dot will be taken as a section indicator
 * (for example, 1., 2., or 12.).
 * As well, a line beginning with a subsection-style series of numbers
 * will also be taken as a section indicator, and can be used to
 * indicate subsections (for example, 1.1, or 1.1.12).
