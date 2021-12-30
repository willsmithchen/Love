/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2005-2019 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.atinbo.common;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class provides static helper methods for {@link CharSequence}.
 */
public final class Strings {

    /**
     * 空字符串
     */
    private static final String NULLSTR = "";
    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * Check whether the given string value is empty. The value is empty if null or length is 0.
     *
     * @param value the string value to test
     * @return true if empty false otherwise
     */
    public static boolean isEmpty(CharSequence value) {
        return value == null || value.length() == 0;
    }

    /**
     * Check whether the given string value is not empty. The value is empty if null or length is 0.
     *
     * @param value the string value to test
     * @return true if not empty false otherwise
     */
    public static boolean notEmpty(CharSequence value) {
        return !isEmpty(value);
    }

    /**
     * Check whether the given string value is blank. The value is blank if null or contains white
     * spaces only.
     *
     * @param value the string value to test
     * @return true if empty false otherwise
     */
    public static boolean isBlank(CharSequence value) {
        if (isEmpty(value)) {
            return true;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether the given string value is not blank. The value is blank if null or contains white
     * spaces only.
     *
     * @param value the string value to test
     * @return true if not empty false otherwise
     */
    public static boolean notBlank(CharSequence value) {
        return !isBlank(value);
    }

    /**
     * Remove diacritics (accents) from a {@link CharSequence}.
     *
     * @param value the string to be stripped
     * @return text with diacritics removed
     */
    public static String stripAccent(CharSequence value) {
        if (value == null) {
            return null;
        }
        if (isEmpty(value)) {
            return value.toString();
        }
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replace('\u0141', 'L')
                .replace('\u0142', 'l')
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    /**
     * Strip the leading indentation from the given text.
     *
     * <p>The line with the least number of leading spaces determines the number to remove. Lines only
     * containing whitespace are ignored when calculating the number of leading spaces to strip.
     *
     * @param text the text to strip indent from
     * @return stripped text
     */
    public static String stripIndent(CharSequence text) {
        if (text == null) {
            return null;
        }
        if (isBlank(text)) {
            return text.toString();
        }

        final String[] lines = text.toString().split("\\n");
        final StringBuilder builder = new StringBuilder();

        int leading = -1;
        for (String line : lines) {
            if (isBlank(line)) {
                continue;
            }
            int index = 0;
            int length = line.length();
            if (leading == -1) {
                leading = length;
            }
            while (index < length && index < leading && Character.isWhitespace(line.charAt(index))) {
                index++;
            }
            if (leading > index) {
                leading = index;
            }
        }

        for (String line : lines) {
            if (!isBlank(line)) {
                builder.append(leading <= line.length() ? line.substring(leading) : "");
            }
            if (lines.length > 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    /**
     * Strip leading whitespace/control characters followed by '|' from every line in the given text.
     *
     * @param text the text to strip the margin from
     * @return the stripped String
     */
    public static String stripMargin(CharSequence text) {
        if (text == null) {
            return null;
        }
        return Stream.of(text.toString().split("\n"))
                .map(line -> line.replaceFirst("^\\s+\\|", ""))
                .collect(Collectors.joining("\n"));
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(StringUtils.trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }
}
