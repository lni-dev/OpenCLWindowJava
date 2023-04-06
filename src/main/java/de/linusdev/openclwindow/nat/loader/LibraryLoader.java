/*
 * Copyright (c) 2023 Linus Andera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.linusdev.openclwindow.nat.loader;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class LibraryLoader {

    private final static @NotNull Path EXECUTION_PATH = Paths.get(System.getProperty("user.dir"));

    private final @NotNull String mainLibResourcePath;
    private final @NotNull String @NotNull[] dependentLibResourcePaths;

    private boolean exported;
    private Path mainLibPath = null;

    public LibraryLoader(@NotNull String mainLibResourcePath, @NotNull String @NotNull ... dependentLibResourcePaths) {
        this.mainLibResourcePath = mainLibResourcePath;
        this.dependentLibResourcePaths = dependentLibResourcePaths;

    }

    /**
     * Exports the libraries temporarily outside the jar file.
     */
    public void export() throws IOException {
        mainLibPath = exportResourceAsTempFile(mainLibResourcePath);

        for(@NotNull String p : dependentLibResourcePaths) {
            exportResourceAsTempFile(p);
        }

        exported = true;
    }

    public void load() throws IOException {
        if(!exported) export();
        System.load(mainLibPath.toString());
    }



    private static @NotNull Path exportResourceAsTempFile(@NotNull String resourcePath) throws IOException {
        Path res = Paths.get(resourcePath);

        Path tempFilePath = EXECUTION_PATH.resolve(res.getFileName());
        exportResource(res, tempFilePath);

        return tempFilePath;
    }

    private static void exportResource(@NotNull Path resourcePath, @NotNull Path destination) throws IOException {
        try(InputStream in = LibraryLoader.class.getClassLoader().getResourceAsStream(resourcePath.toString())) {

            if(in == null)
                throw new IllegalArgumentException("No resource found for given path: " + resourcePath);

            Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
