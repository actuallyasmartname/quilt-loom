/*
 * This file is part of Quilt Loom, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016, 2017, 2018 FabricMC, 2021 QuiltMC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.quiltmc.loom.configuration.providers.minecraft;

import java.io.File;

import org.gradle.api.Project;

import org.quiltmc.loom.LoomGradleExtension;
import org.quiltmc.loom.configuration.providers.MinecraftProvider;
import org.quiltmc.loom.util.Constants;

public class MinecraftLibraryProvider {
	public File MINECRAFT_LIBS;

	public void provide(MinecraftProvider minecraftProvider, Project project) {
		MinecraftVersionMeta versionInfo = minecraftProvider.getVersionInfo();

		initFiles(project, minecraftProvider);

		for (MinecraftVersionMeta.Library library : versionInfo.getLibraries()) {
			if (library.isValidForOS() && !library.hasNatives() && library.getArtifact() != null) {
				project.getDependencies().add(Constants.Configurations.MINECRAFT_DEPENDENCIES, project.getDependencies().module(library.getName()));
			}
		}
	}

	private void initFiles(Project project, MinecraftProvider minecraftProvider) {
		LoomGradleExtension extension = project.getExtensions().getByType(LoomGradleExtension.class);
		MINECRAFT_LIBS = new File(extension.getUserCache(), "libraries");
	}
}