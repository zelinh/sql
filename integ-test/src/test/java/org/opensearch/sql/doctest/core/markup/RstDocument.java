/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */


package org.opensearch.sql.doctest.core.markup;

import com.google.common.base.Strings;
import java.io.PrintWriter;

/**
 * ReStructure Text document
 */
public class RstDocument implements Document {

  private final PrintWriter docWriter;

  public RstDocument(PrintWriter docWriter) {
    this.docWriter = docWriter;
  }

  @Override
  public Document section(String title) {
    return printTitleWithUnderline(title, "=");
  }

  @Override
  public Document subSection(String title) {
    return printTitleWithUnderline(title, "-");
  }

  @Override
  public Document paragraph(String text) {
    return println(text);
  }

  @Override
  public Document codeBlock(String description, String code) {
    if (!Strings.isNullOrEmpty(code)) {
      return println(description + "::", indent(code));
    }
    return this;
  }

  @Override
  public Document table(String description, String table) {
    if (!Strings.isNullOrEmpty(table)) {
      // RST table is different and not supposed to indent
      return println(description + ":", table);
    }
    return this;
  }

  @Override
  public Document image(String description, String filePath) {
    return println(
        description + ":",
        ".. image:: " + filePath
    );
  }

  @Override
  public void close() {
    docWriter.close();
  }

  private Document printTitleWithUnderline(String title, String underlineChar) {
    return print(
        title,
        Strings.repeat(underlineChar, title.length())
    );
  }

  /**
   * Print each line with a blank line at last
   */
  private Document print(String... lines) {
    for (String line : lines) {
      docWriter.println(line);
    }
    docWriter.println();
    return this;
  }

  /**
   * Print each line with a blank line followed
   */
  private Document println(String... lines) {
    for (String line : lines) {
      docWriter.println(line);
      docWriter.println();
    }
    return this;
  }

  private String indent(String text) {
    return "\t" + text.replaceAll("\\n", "\n\t");
  }

}
