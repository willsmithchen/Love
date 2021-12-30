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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides helper methods to find version information of axelor projects.
 */
public final class Versions {

    private static final Pattern VERSION_PATTERN =
            Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)(?:\\-rc(\\d+))?(-SNAPSHOT)?$");
    private static Version version;

    /**
     * Get the Axelor SDK version.
     *
     * @return an instance of {@link Version}
     */
    public static Version getVersion() {
        if (version == null) {
            version = createVersion();
        }
        return version;
    }

    private static Version createVersion() {
        String version = Versions.class.getPackage().getImplementationVersion();
        if (version == null) {
            return Version.UNKNOWN;
        }
        return new Version(version);
    }

    /**
     * This class stores version details of axelor modules.
     */
    public static class Version {

        public static final Version UNKNOWN = new Version("0.0.0");

        public final String version;

        // feature version (major.minor)
        public final String feature;

        public final int major;

        public final int minor;

        public final int patch;

        public final int rc;

        public final boolean snapshot;

        Version(String version) {
            final Matcher matcher = VERSION_PATTERN.matcher(version.trim());
            if (!matcher.matches()) {
                throw new IllegalStateException("Invalid version string.");
            }
            this.version = version.trim();
            this.major = Integer.parseInt(matcher.group(1));
            this.minor = Integer.parseInt(matcher.group(2));
            this.patch = Integer.parseInt(matcher.group(3));
            this.rc = matcher.group(4) != null ? Integer.parseInt(matcher.group(4)) : 0;
            this.feature = String.format("%s.%s", major, minor);
            this.snapshot = matcher.group(5) != null;
        }

        @Override
        public String toString() {
            return version;
        }
    }
}
